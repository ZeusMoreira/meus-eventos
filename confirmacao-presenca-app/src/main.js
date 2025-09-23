import { createApp } from 'vue'
import { createPinia } from 'pinia'
import './reset.css'
import './main.css'
import '@mdi/font/css/materialdesignicons.css'

import App from './App.vue'
import router from './router'

import Toast, { POSITION } from 'vue-toastification'
import 'vue-toastification/dist/index.css'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(Toast, {
  position: POSITION.TOP_RIGHT,
  timeout: 3000,
})
app.mount('#app')
