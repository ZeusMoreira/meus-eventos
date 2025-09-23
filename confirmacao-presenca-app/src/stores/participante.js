import { defineStore } from 'pinia'
import api from '@/interceptor/axios'

export const useParticipanteStore = defineStore('participante', {
  state: () => ({
    page: 0,
    itemsPerPage: 10,
    totalPages: 0,
    totalElements: 0,
    numberOfElements: 0,
    filtro: '',
  }),

  actions: {
    async listarParticipantes(codigo) {
      const response = await api.get(`/admin/eventos/${codigo}/participantes`, {
        params: {
          page: this.page,
          size: this.itemsPerPage,
          filtro: this.filtro,
        },
      })

      this.totalPages = response.data.totalPages
      this.totalElements = response.data.totalElements
      this.numberOfElements = response.data.numberOfElements
      return response.data
    },

    async confirmarPresenca(codigo, nome, cpf) {
      await api.post(`/eventos/${codigo}/participantes`, {
        nome: nome,
        cpf: cpf,
      })
    },

    async excluirParticipante(codigo, idParticipante) {
      await api.delete(`/admin/eventos/${codigo}/participantes/${idParticipante}`)
    },

    resetarPaginacao() {
      this.page = 0
      this.itemsPerPage = 10
      this.totalPages = 0
      this.totalElements = 0
      this.numberOfElements = 0
      this.filtro = ''
    }
  },
})
