<template>
  <div class="confirmacao-container">
    <h1 class="confirmacao-container__titulo">Bem vindo!</h1>
    <formulario
      @submeter="confirmarPresenca"
      titulo="Confirmar Presença"
      :schema="schemaCadastroParticipante"
    >
      <template #campos>
        <textfield name="nome" label="Nome" placeholder="Digite seu nome" />
        <textfield
          name="cpf"
          label="CPF"
          placeholder="Digite seu CPF"
          :mascara="{ mask: '000.000.000-00' }"
          eh-cpf
        />
      </template>
    </formulario>
    <router-link to="/home/login" class="admin-link">área administrativa</router-link>
  </div>
</template>

<script setup>
import Formulario from '@/components/Formulario.vue'
import { useParticipanteStore } from '../../stores/participante.js'
import { useToast } from 'vue-toastification'
import { useRoute } from 'vue-router'
import Textfield from '@/components/Textfield.vue'
import { schemaCadastroParticipante } from '@/validations/schemaCadastroParticipante.js'

const participanteStore = useParticipanteStore()
const toast = useToast()
const route = useRoute()
const confirmarPresenca = async (dados) => {
  try {
    await participanteStore.confirmarPresenca(route.params.codigo, dados.nome, dados.cpf)
    toast.success('Presença confirmada com sucesso!')
  } catch (e) {
    if (e.response.data.mensagem) {
      toast.error(e.response.data.mensagem)
      return
    }
    toast.error(e.response.data)
  }
}
</script>

<style scoped>
.confirmacao-container {
  margin-left: 1.6rem;
  margin-right: 1.6rem;
  display: flex;
  flex-direction: column;
  text-align: center;
  justify-content: space-around;
  height: 100vh;
}

.confirmacao-container__titulo {
  font-weight: bold;
  font-size: 2.4rem;
  color: var(--cor-primaria);
}

.admin-link {
  display: block;
  color: var(--cor-primaria);
  font-weight: normal;
  font-size: 2rem;
}
</style>
