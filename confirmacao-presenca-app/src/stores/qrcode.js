import { defineStore } from 'pinia'
import api from '@/interceptor/axios'

export const useQrCodeStore = defineStore('qrcode', {
  actions: {
    async gerarQrCode(codigo) {
      const { data } = await api.get(`/admin/eventos/${codigo}/qr-code`)
      return data
    },
  },
})
