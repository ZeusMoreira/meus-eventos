<template>
  <div class="adicao-evento-container">
    <loading v-model:active="isLoading" :color="getCssVar('--cor-primaria')" />
    <div class="header">
      <i class="mdi mdi-arrow-left icone-back" @click="tratarEventoVoltar"></i>
      <h1 class="container__titulo">Adicionar Evento</h1>
    </div>
    <formulario
      class="formulario-adicao-evento"
      titulo="Adição de Evento"
      @submeter="cadastrar"
      :schema="schemaAdicaoEvento"
      texto-botao="Adicionar"
    >
      <template #campos>
        <textfield name="titulo" label="Título" placeholder="Digite o título do evento" />
        <textfield
          name="data"
          label="Data"
          placeholder="Digite a data do evento"
          type="text"
          :mascara="{ mask: '00/00/0000' }"
        />
      </template>
    </formulario>
    <div></div>
  </div>
</template>

<script setup>
import Formulario from '@/components/Formulario.vue'
import { useToast } from 'vue-toastification'
import { schemaAdicaoEvento } from '@/validations/schemaAdicaoEvento.js'
import Textfield from '@/components/Textfield.vue'
import router from '@/router/index.js'
import { ref } from 'vue'
import Loading from 'vue-loading-overlay'
import 'vue-loading-overlay/dist/css/index.css'
import { useEventoStore } from '@/stores/evento.js'
import { useDate } from '@/composables/useDate.js'

const eventoStore = useEventoStore()
const isLoading = ref(false)
const toast = useToast()
const { formatarDataISO } = useDate()
const getCssVar = (variavel) => {
  return getComputedStyle(document.documentElement).getPropertyValue(variavel).trim()
}

async function tratarEventoVoltar() {
  await router.push('/home/admin/gerenciamento')
}

const cadastrar = async (dados) => {
  try {
    isLoading.value = true
    await eventoStore.criarEvento(dados.titulo, formatarDataISO(dados.data))
    toast.success('Evento cadastrado com sucesso!')
    await tratarEventoVoltar()
  } catch (e) {
    if(e.response.data.errors.length > 0) {
      toast.error(`O Campo ${e.response.data.errors[0].campo} ${e.response.data.errors[0].erro}.`)
    } else {
      toast.error(e.response.data.mensagem)
    }
  } finally {
    isLoading.value = false
  }
}
</script>

<style scoped>
.adicao-evento-container {
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
}

.icone-back {
  font-size: 2.4rem;
  color: var(--cor-primaria);
  align-self: start;
  margin-bottom: 3.4rem;
}

.header {
  display: flex;
  flex-direction: column;
  justify-content: start;
}
</style>
