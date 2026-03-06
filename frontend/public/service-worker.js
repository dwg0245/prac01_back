// 푸시 알림이 뜨면 콘솔에 출력
self.addEventListener("push", (event) =>{
    console.log(event.data)

    // 알림 띄우는 코드 '
    // 우리사이트가 아니어도 웹 브라우저가 켜져 있으면 알림을 받을 수 잇다.
    // event.waitUtill
})