<template>
  <modal-confirmacao
    :exibir="modalAberto"
    titulo="Deseja realmente confirmar esta ação ?"
    :conteudo="'Após excluir o participante não será possível recuperar.'"
    @fechar="fecharModal"
    @confirmar="excluirParticipante"
  >
  </modal-confirmacao>
  <div class="container">
    <loading v-model:active="isLoading" :color="getCssVar('--cor-primaria')" />
    <div class="header">
      <i class="mdi mdi-arrow-left icone-back" @click="tratarEventoVoltar"></i>
      <h1 class="container__titulo">{{ evento.titulo }}</h1>
      <botao-icone
        style="margin-bottom: 2.1rem"
        texto="Adicionar Participante"
        :icon="`mdi mdi-calendar-plus`"
        @clicar="tratarEventoAdicionarParticipante"
      ></botao-icone>
      <botao-icone
        class="botao-icone"
        texto="Gerar QR Code"
        :icon="`mdi mdi-qrcode`"
        :background-color="'var(--cor-primaria)'"
        :cor="'var(--cor-branco)'"
        @clicar="tratarEventoGerarQrCode"
      ></botao-icone>
      <textfield
        ref="filtroRef"
        class="filtro"
        name="filtro"
        placeholder="Busque participantes por CPF ou Nome"
        icon-left="mdi-magnify"
        @alteracaoCampo="tratarEventoAlteracaoCampo"
      />
      <listagem-participantes-tabela
        :participantes="participantes"
        @excluir-participante="tratarEventoExcluirParticipante"
      ></listagem-participantes-tabela>
      <paginacao
        v-if="participantes.length"
        class="paginacao"
        :pagina-atual="participanteStore.page"
        :total-paginas="participanteStore.totalPages"
        :total-elements="participanteStore.totalElements"
        :numero-de-elementos="participanteStore.numberOfElements"
        @paginar="tratarEventoPaginar"
        @selecionar-pagina="tratarEventoSelecaoPagina"
        :cor-habilitado="'var(--preto-tabela)'"
      ></paginacao>
    </div>
  </div>
</template>

<script setup>
import BotaoIcone from '@/components/BotaoIcone.vue'
import { onMounted, ref } from 'vue'
import { useAuthStore } from '@/stores/auth.js'
import Loading from 'vue-loading-overlay'
import router from '@/router/index.js'
import 'vue-loading-overlay/dist/css/index.css'
import { useEventoStore } from '@/stores/evento.js'
import { useParticipanteStore } from '@/stores/participante.js'
import Textfield from '@/components/Textfield.vue'
import { useRoute } from 'vue-router'
import ListagemParticipantesTabela from '@/pages/participante/ListagemParticipantesTabela.vue'
import Paginacao from '@/components/Paginacao.vue'
import { useToast } from 'vue-toastification'
import ModalConfirmacao from '@/components/ModalConfirmacao.vue'
import ModalVerMais from '@/components/ModalVerMais.vue'

const isLoading = ref(false)
const auth = useAuthStore()
const eventoStore = useEventoStore()
const participanteStore = useParticipanteStore()
const route = useRoute()
const toast = useToast()

const evento = ref({})
const participantes = ref([])
const modalAberto = ref(false)
const participante = ref(null)
const filtroRef = ref(null)

onMounted(async () => {
  participanteStore.resetarPaginacao()
  await buscarEvento()
})

const getCssVar = (variavel) => {
  return getComputedStyle(document.documentElement).getPropertyValue(variavel).trim()
}

const buscarEvento = async () => {
  const { data } = await eventoStore.listarEvento(route.params.codigo)
  evento.value = data
  participantes.value = evento.value.participantes.content
}

const buscarParticipantes = async () => {
  const { content } = await participanteStore.listarParticipantes(route.params.codigo)
  participantes.value = content
}

const excluirParticipante = async () => {
  try {
    await participanteStore.excluirParticipante(route.params.codigo, participante.value)
    toast.success('Participante excluído com sucesso!')
    fecharModal()
    participanteStore.resetarPaginacao()
    filtroRef.value?.limpar()
    await buscarParticipantes()
  } catch (e) {
    toast.error(e.response.data.mensagem)
  } finally {
    isLoading.value = false
  }
}

const tratarEventoAdicionarParticipante = async () => {
  await router.push(`/home/admin/eventos/${evento.value.codigo}/adicao-participante`)
}

async function tratarEventoAlteracaoCampo(valor) {
  participanteStore.resetarPaginacao()
  participanteStore.filtro = valor
  await buscarParticipantes()
}
async function tratarEventoPaginar(direcao) {
  if (direcao === 'direita') {
    participanteStore.page++
  } else {
    participanteStore.page--
  }
  await buscarParticipantes()
}

async function tratarEventoSelecaoPagina(pagina) {
  participanteStore.page = pagina
  await buscarParticipantes()
}

async function tratarEventoVoltar() {
  await router.push('/home/admin')
}

async function tratarEventoGerarQrCode() {
  await router.push(`/home/admin/eventos/${evento.value.codigo}/qr-code`)
}

async function tratarEventoExcluirParticipante(idParticipante) {
  abrirModal(idParticipante)
}

function abrirModal(idParticipante) {
  participante.value = idParticipante
  modalAberto.value = true
}

function fecharModal() {
  participante.value = null
  modalAberto.value = false
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
