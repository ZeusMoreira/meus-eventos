<template>
  <div class="container">
    <loading v-model:active="isLoading" :color="getCssVar('--cor-primaria')" />
    <div class="header">
      <h1 class="container__titulo">Área Administrativa</h1>
      <botao-icone
        class="botao-icone"
        texto="Gerenciar Eventos"
        :icon="`mdi mdi-calendar-edit`"
        :style="eventos.length > 0 ? 'margin-bottom: 2rem' : ''"
        @clicar="tratarEventoGerenciarEventos"
      ></botao-icone>
      <listagem-ultimos-eventos
        v-if="eventos.length > 0"
        :eventos="eventos"
        @acessar-evento="tratarEventoAcessarEvento"
      />
    </div>
    <listagem-ultimos-eventos
      v-if="eventos.length === 0"
      :eventos="eventos"
      @acessar-evento="tratarEventoAcessarEvento"
    />
    <div class="acoes-conta">
      <span class="admin-link" @click="tratarEventoSair">sair</span>
      <span class="admin-link" @click="recuperarSenha">alterar senha</span>
    </div>
  </div>
</template>

<script setup>
import BotaoIcone from '@/components/BotaoIcone.vue'
import ListagemUltimosEventos from '@/pages/evento/ListagemUltimosEventos.vue'
import Paginacao from '@/components/Paginacao.vue'
import { onMounted, ref } from 'vue'
import { useAuthStore } from '@/stores/auth.js'
import { useToast } from 'vue-toastification'
import Loading from 'vue-loading-overlay'
import router from '@/router/index.js'
import 'vue-loading-overlay/dist/css/index.css'
import { useEventoStore } from '@/stores/evento.js'

const isLoading = ref(false)
const toast = useToast()
const auth = useAuthStore()
const eventoStore = useEventoStore()

const eventos = ref([])

onMounted(async () => {
  await buscarEventos()
})

const getCssVar = (variavel) => {
  return getComputedStyle(document.documentElement).getPropertyValue(variavel).trim()
}

const buscarEventos = async () => {
  try {
    isLoading.value = true
    const { content } = await eventoStore.listarEventos()
    eventos.value = content
  } catch (e) {
    toast.error(e.response.data.mensagem)
  } finally {
    isLoading.value = false
  }
}
const tratarEventoSair = async () => {
  try {
    isLoading.value = true
    auth.logout()
    await router.push('/home/login')
    toast.success('Logout realizado com sucesso!')
  } catch (e) {
    toast.error('Houve um erro ao realizar o logout!')
  } finally {
    isLoading.value = false
  }
}

const recuperarSenha = async () => {
  try {
    isLoading.value = true
    await auth.recuperarSenha()
    toast.success('Email de recuperação enviado para o email cadastrado.')
  } catch (e) {
    toast.error(e.response.data.mensagem)
  } finally {
    isLoading.value = false
  }
}

const tratarEventoGerenciarEventos = async () => {
  await router.push('/home/admin/gerenciamento')
}

async function tratarEventoAcessarEvento(evento) {
  await router.push(`/home/admin/eventos/${evento.codigo}`)
}
</script>

<style scoped>
.header {
  display: flex;
  flex-direction: column;
  gap: 3.3rem;
}

.container {
  padding-right: 1.6rem;
  padding-left: 1.6rem;
  min-height: 100vh;
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

.container__titulo {
  font-weight: bold;
  font-size: 2.4rem;
  color: var(--cor-primaria);
}

.acoes-conta {
  display: flex;
  justify-content: space-around;
  margin-left: 1.6rem;
  margin-right: 1.6rem;
}

.admin-link {
  display: block;
  color: var(--cor-primaria);
  font-weight: normal;
  font-size: 2rem;
  text-decoration: underline;
}
</style>
