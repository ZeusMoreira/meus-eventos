<template>
  <div class="login-container">
    <loading v-model:active="isLoading" :color="getCssVar('--cor-primaria')" />
    <h1 class="login-container__titulo">Bem vindo!</h1>
    <formulario
      class="formulario-login"
      titulo="Entrar como Administrador"
      @submeter="logar"
      :schema="schemaLoginAdmin"
      texto-botao="Entrar"
    >
      <template #campos>
        <textfield name="email" label="Email" placeholder="Digite o seu email" />
        <textfield
          name="senha"
          label="Senha"
          placeholder="Digite a sua senha"
          :type="type"
          :icon-right="iconRight"
          @click-icon-right="alternarVisibilidadeSenha"
        />
        <span class="esqueceu-senha-link" @click="recuperarSenha">esqueceu sua senha ?</span>
      </template>
    </formulario>
    <div></div>
  </div>
</template>

<script setup>
import Formulario from '@/components/Formulario.vue'
import { useToast } from 'vue-toastification'
import { schemaLoginAdmin } from '@/validations/schemaLoginAdmin.js'
import Textfield from '@/components/Textfield.vue'
import { useAuthStore } from '@/stores/auth.js'
import router from '@/router/index.js'
import { computed, ref } from 'vue'
import Loading from 'vue-loading-overlay'
import 'vue-loading-overlay/dist/css/index.css'

const isLoading = ref(false)
const isPasswordVisible = ref(false)
const toast = useToast()
const auth = useAuthStore()
const getCssVar = (variavel) => {
  return getComputedStyle(document.documentElement).getPropertyValue(variavel).trim()
}

const type = computed(() => {
  return isPasswordVisible.value ? 'text' : 'password'
})

const iconRight = computed(() => {
  return isPasswordVisible.value ? 'mdi-eye-off' : 'mdi-eye'
})

const logar = async (dados) => {
  try {
    isLoading.value = true
    const conseguiuLogar = await auth.login(dados.email, dados.senha)
    if (conseguiuLogar) {
      await router.push('/home/admin')
    }
  } catch (e) {
    toast.error(e.response.data.mensagem)
  } finally {
    isLoading.value = false
  }
}

const alternarVisibilidadeSenha = () => {
  isPasswordVisible.value = !isPasswordVisible.value
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
  cursor: pointer;
}
</style>
