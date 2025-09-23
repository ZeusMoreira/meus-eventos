import { defineStore } from 'pinia'

export const useNavegacaoStore = defineStore('navegacao', {
  state: () => ({
    ultimaRota: sessionStorage.getItem('ultimaRota') || null,
  }),
  actions: {
    setUltimaRota(rota) {
      this.ultimaRota = rota
      sessionStorage.setItem('ultimaRota', rota)
    },
  },
})
