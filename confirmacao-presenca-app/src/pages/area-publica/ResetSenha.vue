<template>
  <div class="login-container">
    <loading v-model:active="isLoading" />
    <h1 class="login-container__titulo">Alteração de senha</h1>
    <formulario
      class="formulario-login"
      titulo="Altere sua senha"
      @submeter="recuperarSenha"
      :schema="schemaResetSenha"
      texto-botao="Alterar"
    >
      <template #campos>
        <textfield
          name="senha"
          label="Senha"
          placeholder="Digite sua senha"
          :type="type"
          :icon-right="iconRight"
          @click-icon-right="alternarVisibilidadeSenha"
        />
        <textfield
          name="confirmarSenha"
          label="Confirmar Senha"
          placeholder="Confirme sua senha"
          :type="typeConfirmacaoSenha"
          :icon-right="iconRightConfirmPassword"
          @click-icon-right="alternarVisibilidadeConfirmacaoSenha"
        />
      </template>
    </formulario>
    <div></div>
  </div>
</template>

<script setup>
import Formulario from '@/components/Formulario.vue'
import { useToast } from 'vue-toastification'
import { schemaResetSenha } from '@/validations/schemaResetSenha.js'
import Textfield from '@/components/Textfield.vue'
import { useAuthStore } from '@/stores/auth.js'
import router from '@/router/index.js'
import { useRoute } from 'vue-router'
import { computed, ref } from 'vue'
import Loading from 'vue-loading-overlay'
import 'vue-loading-overlay/dist/css/index.css'

const isLoading = ref(false)
const toast = useToast()
const auth = useAuthStore()
const route = useRoute()
const token = route.query.token
const isPasswordVisible = ref(false)
const isConfirmPasswordVisible = ref(false)

const type = computed(() => {
  return isPasswordVisible.value ? 'text' : 'password'
})

const iconRight = computed(() => {
  return isPasswordVisible.value ? 'mdi-eye-off' : 'mdi-eye'
})

const alternarVisibilidadeSenha = () => {
  isPasswordVisible.value = !isPasswordVisible.value
}

const iconRightConfirmPassword = computed(() => {
  return isConfirmPasswordVisible.value ? 'mdi-eye-off' : 'mdi-eye'
})

const alternarVisibilidadeConfirmacaoSenha = () => {
  isConfirmPasswordVisible.value = !isConfirmPasswordVisible.value
}

const typeConfirmacaoSenha = computed(() => {
  return isConfirmPasswordVisible.value ? 'text' : 'password'
})

const recuperarSenha = async (dados) => {
  try {
    isLoading.value = true
    if (dados.senha === dados.confirmarSenha) {
      await auth.alterarSenha(token, dados.senha)
      await auth.logout()
      await router.push('/home/login')
      toast.success('Senha alterada com sucesso.')
      return
    }
    toast.error('As senhas devem coincidir.')
  } catch (e) {
    toast.error(e.response.data.mensagem)
    await router.push('/home/login')
  } finally {
    isLoading.value = false
  }
}
</script>

<style scoped>
.login-container {
  margin-left: 1.6rem;
  margin-right: 1.6rem;
  display: flex;
  flex-direction: column;
  text-align: center;
  justify-content: space-around;
  height: 100vh;
}

.login-container__titulo {
  font-weight: bold;
  font-size: 2.4rem;
  color: var(--cor-primaria);
}

.esqueceu-senha-link {
  align-self: start;
  display: block;
  color: var(--cor-primaria);
  font-weight: normal;
  font-size: 1.6rem;
  text-decoration: underline;
}
</style>
