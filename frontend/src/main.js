
import { createApp } from 'vue'
import { createPinia } from 'pinia'

import AppWeb from './App.vue'

const app = createApp(AppWeb)

app.use(createPinia())


app.mount('#app')
