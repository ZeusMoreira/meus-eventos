import { defineStore } from 'pinia'
import api from '@/interceptor/axios'
import { useParticipanteStore } from './participante'

export const useEventoStore = defineStore('evento', {
  state: () => ({
    page: 0,
    itemsPerPage: 5,
    totalPages: 0,
    totalElements: 0,
    numberOfElements: 0,
    filtro: '',
  }),

  actions: {
    async listarEventos() {
      const response = await api.get('/admin/eventos', {
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

    async listarEvento(codigo) {
      const participanteStore = useParticipanteStore()

      const response = await api.get(`/admin/eventos/${codigo}`, {
        params: {
          page: participanteStore.page,
          size: participanteStore.itemsPerPage,
          filtro: participanteStore.filtro,
        },
      })

      participanteStore.totalPages = response.data.participantes.totalPages
      participanteStore.totalElements = response.data.participantes.totalElements
      participanteStore.numberOfElements = response.data.participantes.numberOfElements
      return response
    },

    async criarEvento(titulo, data) {
      await api.post('/admin/eventos', {
        titulo,
        dataEvento: data,
      })
    },
  },
})
