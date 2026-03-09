<script setup >
import {ref} from "vue";
import { Client } from '@stomp/stompjs'

const localStream = ref(null);
const localVideo = ref(null);
const remoteVideo = ref(null);
const socket = ref(null);
let peerConnection = null;

// 나중에 토큰 가지고 처리하기 / 사용자의 고유id 생성
const senderId =
    Math.random().toString(36).substring(2, 9)

// Stun 서버
const config = {
  iceServers: [
    { urls: 'stun:stun.l.google.com:19302' }
  ]
};

// -------- 3가지 종류의 메세지 ---------------

// 영상 및 오디오 스펙을 상대방에게 제안하는 메세지를 받았을 때 처리하는 함수
const handleOffer = async (data)=>{
  //없을 때 실행
  if(!peerConnection){
    createPeerCannection()
  }
  await peerConnection.setRemoteDescription(
      // 데이터 안에 있는 offer을 가지고 와서 전달
      new RTCSessionDescription(data.offer)
  );
  // 응답 주기
  const answer = await peerConnection.createAnswer();
  await peerConnection.setRemoteDescription(answer);

  // 제안 요청 응답 보내기
  socket.value.publish({
    destination: '/app/webrtc', // 보낼때
    // 누가 보냈는지, 랜덤 id 값 보내기
    body: JSON.stringify({
      type: 'answer',
      offer: answer,
      senderId: senderId
    })
  })

}

// 제안에 대한 응답을 받았을 때 처리하는 함수, 영상 및 오디오 스펙을 담아서 응답
// 응답을 통일하는 과정이 필요, mp4가 안된다거나 wva가 안되면 통일하는 과정이 필요
const handleAnswer = async (data)=>{
  console.log(data);

  await peerConnection.setRemoteDescription(
      new RTCSessionDescription(data.answer)
  );
}


// 영상 및 오디오 스펙 합의가 끝나면 실제 데이터를 주고받을 네트워크 주소를 교확하는 함수
// 일반적으로 각 티어(클라이언트)는 공유기 내부에 있기 때문에 자신의 실제 공인 IP를 알 수 없다.
// 그래서 STUN 서버를 이용해서 알아낸 자신의 실제 공인 IP, 포트번호를 이용해서 접속 가능한 경로를 교환

// 직접 연결 통신이 아니라 언딘가의 서버로 오면 너의 둘을 연결해 줄게라고 하는 서버가 있다.
// 자신이 알아온 ip를 가지고 서로가 연결 될 수 있도록 공인 ip의 포트 번호로 연결하라고 이어준다.
// 그 다음에는 서로 연결한다. 한번 나갈때 저장해 놓은 ip를 기억해서
const handleCandidata = async (data)=>{
  console.log(data);
  // 알아온걸 가지고
  await peerConnection.addIceCandidata(
      new RTCSessionDescription(data.candidata)
  )
}

const createPeerCannection = () => {
  if(peerConnection){
    return
  }

  peerConnection = new RTCSessionDescription(config)

  // 상대방이 비디오 스트림을 보내게
  // 스턴에 추가
  if(localStream.value) {
    localStream.value.getTracks().forEach((track) => {
      peerConnection.addTrack(track, localStream.value)
    })
  }


  // 스턴로 알아온 서버 후보들을 보내 놓기
  peerConnection.onicecandidata = (event) =>{
    if(event.candidata){
      socket.value.publish({
        destination: '/app/webrtc', // 보낼때
        // 누가 보냈는지, 랜덤 id 값 보내기
        body: JSON.stringify({
          type: 'candidata',
          offer: event.candidata,
          senderId: senderId
        })
      })
    }
  }

  peerConnection.ontrack = (event) => {
    if (remoteVideo.value) {
      remoteVideo.value.srcObject = event.streams[0]
    }
  }
}

// 제안 만들어 보내기
const makeCall = async ()=>{
  createPeerCannection() // 함수로 실행
  // 너네끼리 연결을 해라
  // 제안서 보내기
  const offer = await peerConnection.createOffer();
  peerConnection.setLocalDescription(offer);

  // 메세지 보내기
  socket.value.publish({
    destination: '/app/webrtc', // 보낼때
    // 누가 보냈는지, 랜덤 id 값 보내기
    body: JSON.stringify({
      type: 'offer',
      offer: offer,
      senderId: senderId
    })
  })
}

// 카메라 시작
const startCamera =  async () => {
    localStream.value = await navigator.mediaDevices.getUserMedia({
      video: true,
      audio: true
    });
    localVideo.value.srcObject = localStream.value;

  // 웹소켓 연결
  socket.value = new Client({
    brokerURL: "ws://localhost:5173/ws",
  });

  socket.value.onConnect = () =>{
    console.log(("웹 소켓 연결 성공"));

    // 주고받는 메세지
    socket.value.subscribe('/topic/webrtc', async (message)=>{
      console.log(message)
      const data = JSON.parse(message.body);

      // 전달 받은 메세지에 senderID가 내거와 같으면 따로 저리 안하게 하기
      if(data.senderId === senderId){
        return;
      }

      if(data.type == 'offer'){
        await handleOffer(data);
      }else if(data.type == 'answer'){
        await handleAnswer(data);
      }else if (data.type =='candidate'){
        await handleCandidata(date)
      }
    })
  }
  socket.value.activate()
}

</script>

<template>
  <h1>기본 WebRTC 화상 공유 데모</h1>
  <div class="controls">
    <button @click="startCamera">카메라 시작</button>
    <button @click="makeCall">연결 요청</button>
    <button id="btnHangup" disabled>연결 종료</button>
  </div>

  <div class="videos">
    <div>
      <div>내 화면</div>
      <video ref="localVideo" autoplay playsinline muted></video>
    </div>
    <div>
      <div>상대 화면</div>
      <video ref="remoteVideo" autoplay playsinline></video>
    </div>
  </div>

  <div class="log" id="log"></div>
</template>

<style scoped>
body {
  font-family: system-ui, sans-serif;
  margin: 0;
  padding: 20px;
  background: #f4f4f5;
}

h1 {
  margin-bottom: 10px;
}

.videos {
  display: flex;
  gap: 16px;
  margin-top: 16px;
}

video {
  width: 360px;
  height: 270px;
  background: #000;
  border-radius: 8px;
  object-fit: cover;
}

.controls {
  margin-top: 10px;
  display: flex;
  gap: 8px;
}

button {
  padding: 8px 12px;
  border-radius: 6px;
  border: 1px solid #ddd;
  cursor: pointer;
  background: white;
}

button:disabled {
  opacity: 0.5;
  cursor: default;
}

.log {
  margin-top: 16px;
  padding: 8px;
  border-radius: 6px;
  background: #e5e7eb;
  font-size: 13px;
  max-height: 160px;
  overflow-y: auto;
  white-space: pre-wrap;
}
</style>
