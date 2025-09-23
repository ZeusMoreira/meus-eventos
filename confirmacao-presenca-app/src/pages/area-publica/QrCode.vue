<template>
  <div class="container">
    <loading v-model:active="isLoading" :color="getCssVar('--cor-primaria')" />
    <div class="header">
      <i class="mdi mdi-arrow-left icone-back" @click="tratarEventoVoltar"></i>
      <h1 class="container__titulo">{{ evento.titulo }}</h1>
    </div>
    <img :src="qrCode" />
    <div></div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import Loading from 'vue-loading-overlay'
import router from '@/router/index.js'
import 'vue-loading-overlay/dist/css/index.css'
import { useQrCodeStore } from '@/stores/qrcode.js'
import { useEventoStore } from '@/stores/evento.js'
import { useRoute } from 'vue-router'
import { useNavegacaoStore } from '@/stores/navegacao.js'
const getCssVar = (variavel) => {
  return getComputedStyle(document.documentElement).getPropertyValue(variavel).trim()
}

const isLoading = ref(false)
const eventoStore = useEventoStore()
const qrCodeStore = useQrCodeStore()
const nav = useNavegacaoStore()
const route = useRoute()

const evento = ref({})
const qrCode = ref('')

onMounted(async () => {
  await buscarEvento()
  await gerarQrCode()
})

const buscarEvento = async () => {
  const { data } = await eventoStore.listarEvento(route.params.codigo)
  evento.value = data
}
async function tratarEventoVoltar() {
  if (nav.ultimaRota) {
    await router.push(nav.ultimaRota)
    return
  }
  await router.push('/home/admin/gerenciamento')
}

async function gerarQrCode() {
  const response = await qrCodeStore.gerarQrCode(route.params.codigo)
  qrCode.value = response.qrCode
}
</script>

<style scoped>
.header {
  display: flex;
  flex-direction: column;
  justify-content: start;
}

.container {
  padding: 4rem 1.6rem;
  min-height: 100vh;
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.container__titulo {
  font-weight: bold;
  font-size: 2.4rem;
  color: var(--cor-primaria);
  margin-bottom: 3.2rem;
}

.icone-back {
  font-size: 2.4rem;
  color: var(--cor-primaria);
  align-self: start;
  margin-bottom: 3.4rem;
}

img {
  align-self: center;
}
</style>
