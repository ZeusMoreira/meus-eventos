import { defineStore } from 'pinia'
import api from '@/interceptor/axios'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || null,
    user: null,
  }),

  actions: {
    async login(email, senha) {
      const response = await api.post('/auth/login', { email, senha })

      this.token = response.data.token
      localStorage.setItem('token', this.token)

      return true
    },

    logout() {
      this.token = null
      localStorage.removeItem('token')
    },

    isAuthenticated() {
      return !!this.token
    },

    async recuperarSenha() {
      await api.post('/auth/esqueci-senha')
    },

    async alterarSenha(token, senha) {
      await api.post('/auth/redefinir-senha', { token, senha })
    },
  },
})
