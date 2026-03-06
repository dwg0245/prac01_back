<script setup >
import axios from "axios";

const subscribePush = async ()=>{
  const permission = await Notification.requestPermission()
  if(permission !=="granted"){
    console.log("권한 없음")

    return
  }

  await navigator.serviceWorker.register('/service-worker.js')

  // 저장했던 키 중에 위에 고유한 공개키 저장
  const VAPID_PUBLIC_KEY = 'BNM2x0rEjOkB7N5HCZhXpZ2lWl6DgVhVK-pSyL7cDRkhX-eXPiW6Zt0rEptHGwAxBVBsRf1JBa-n5zZtAVEBhiw'

  const registration = await  navigator.serviceWorker.ready;
  let subscription = await  registration.pushManager.getSubscription()


  // 구독하면 여러 키가 생성이 된다. 이거를 백엔드에 받아서 저장을 행야한다.
  // 이게 웹 브라우저의 고유의 값이다. 이 정보를 db에 저장하고 있다가 이 유저에서 메세지를 보내다.
  // 공개키에 맞는 개인키가 있어야 한다.

  // 구독했는지 확인하는 코드
  if(!subscription){ // 등록이 되어 있지 않으면
    // 구독을 안했으면 구독하게 해준다.
    subscription = await registration.pushManager.subscribe({
      userVisibleOnly: true,
      applicationServerKey: VAPID_PUBLIC_KEY
    })
    console.log(JSON.stringify(subscription))
    await axios.post('http://localhost:8080/push/sub',subscription)
  }
}

</script>

<template>
  <button @click="subscribePush">알림 구독 </button>
</template>

<style scoped>

</style>
