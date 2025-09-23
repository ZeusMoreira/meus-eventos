<template>
  <div class="confirmacao-container">
    <div class="header">
      <i class="mdi mdi-arrow-left icone-back" @click="tratarEventoVoltar"></i>
      <h1 class="container__titulo">Adicionar Participante</h1>
    </div>
    <formulario
      @submeter="adicionarParticipante"
      titulo="Adição de Participante"
      :schema="schemaCadastroParticipante"
      :texto-botao="'Adicionar'"
    >
      <template #campos>
        <textfield name="nome" label="Nome" placeholder="Digite o nome do participante" />
        <textfield
          name="cpf"
          label="CPF"
          placeholder="Digite o CPF do participante"
          :mascara="{ mask: '000.000.000-00' }"
          eh-cpf
        />
      </template>
    </formulario>
    <div></div>
  </div>
</template>

<script setup>
import Formulario from '@/components/Formulario.vue'
import { useParticipanteStore } from '../../stores/participante.js'
import { useToast } from 'vue-toastification'
import { useRoute } from 'vue-router'
import Textfield from '@/components/Textfield.vue'
import { schemaCadastroParticipante } from '@/validations/schemaCadastroParticipante.js'
import router from "@/router/index.js";

const participanteStore = useParticipanteStore()
const toast = useToast()
const route = useRoute()
const adicionarParticipante = async (dados) => {
  try {
    await participanteStore.confirmarPresenca(route.params.codigo, dados.nome, dados.cpf)
    toast.success('Participante adicionado com sucesso!')
    await tratarEventoVoltar()
  } catch (e) {
    if (e.response.data.mensagem) {
      toast.error(e.response.data.mensagem)
      return
    }
    toast.error(e.response.data)
  }
}

async function tratarEventoVoltar() {
  await router.push(`/home/admin/eventos/${route.params.codigo}`)
}

</script>

<style scoped>
.confirmacao-container {
  padding: 4rem 1.6rem;
  min-height: 100vh;
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.header {
  display: flex;
  flex-direction: column;
  justify-content: start;
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
</style>
