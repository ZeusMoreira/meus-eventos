<template>
  <div class="container">
    <loading v-model:active="isLoading" :color="getCssVar('--cor-primaria')" />
    <div class="header">
      <i class="mdi mdi-arrow-left icone-back" @click="tratarEventoVoltar"></i>
      <h1 class="container__titulo">Eventos</h1>
      <botao-icone
        class="botao-icone"
        texto="Adicionar Evento"
        :icon="`mdi mdi-calendar-plus`"
        @clicar="tratarEventoAdicionarEvento"
      ></botao-icone>
      <textfield
        class="filtro"
        name="filtro"
        placeholder="Busque um evento por título"
        icon-left="mdi-magnify"
        border-color="var(--cor-primaria)"
        font-color="var(--cor-primaria)"
        @alteracaoCampo="tratarEventoAlteracaoCampo"
      />
      <listagem-eventos
        :eventos="eventos"
        @gerar-qr-code="tratarEventoGerarQrCode"
        @acessar-evento="tratarEventoAcessarEvento"
      ></listagem-eventos>
      <paginacao
        class="paginacao"
        :pagina-atual="eventoStore.page"
        :total-paginas="eventoStore.totalPages"
        :total-elements="eventoStore.totalElements"
        :numero-de-elementos="eventoStore.numberOfElements"
        @paginar="tratarEventoPaginar"
        @selecionar-pagina="tratarEventoSelecaoPagina"
      ></paginacao>
    </div>
  </div>
</template>

<script setup>
import BotaoIcone from '@/components/BotaoIcone.vue'
import Paginacao from '@/components/Paginacao.vue'
import ListagemEventos from '@/pages/evento/ListagemEventos.vue'
import { onMounted, ref } from 'vue'
import { useAuthStore } from '@/stores/auth.js'
import Loading from 'vue-loading-overlay'
import router from '@/router/index.js'
import 'vue-loading-overlay/dist/css/index.css'
import { useEventoStore } from '@/stores/evento.js'
import { useQrCodeStore } from '@/stores/qrcode.js'
import Textfield from '@/components/Textfield.vue'

const isLoading = ref(false)
const auth = useAuthStore()
const eventoStore = useEventoStore()
const qrCodeStore = useQrCodeStore()

const eventos = ref([])

onMounted(async () => {
  await buscarEventos()
})

const getCssVar = (variavel) => {
  return getComputedStyle(document.documentElement).getPropertyValue(variavel).trim()
}

const buscarEventos = async () => {
  const { content } = await eventoStore.listarEventos()
  eventos.value = content
}

async function tratarEventoAdicionarEvento() {
  await router.push('/home/admin/eventos/criacao')
}

async function tratarEventoAlteracaoCampo(valor) {
  eventoStore.filtro = valor
  await buscarEventos()
}
async function tratarEventoPaginar(direcao) {
  if (direcao === 'direita') {
    eventoStore.page++
  } else {
    eventoStore.page--
  }
  await buscarEventos()
}

async function tratarEventoSelecaoPagina(pagina) {
  eventoStore.page = pagina
  await buscarEventos()
}

async function tratarEventoVoltar() {
  await router.push('/home/admin')
}

async function tratarEventoGerarQrCode(evento) {
  await router.push(`/home/admin/eventos/${evento.codigo}/qr-code`)
}

async function tratarEventoAcessarEvento(evento) {
  await router.push(`/home/admin/eventos/${evento.codigo}`)
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

.botao-icone {
  margin-bottom: 5.4rem;
}

.paginacao {
  margin-top: 4.6rem;
}

.filtro {
  margin-bottom: 3.2rem;
}
</style>
