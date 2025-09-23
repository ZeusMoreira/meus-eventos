import * as yup from 'yup'
import {useDate} from "@/composables/useDate.js";

const { formatarDataISO } = useDate()

export const schemaAdicaoEvento = yup.object({
  titulo: yup.string().required('O Título é obrigatório'),
  data: yup
    .string()
    .required('A data é obrigatória')
    .matches(/^\d{2}\/\d{2}\/\d{4}$/, 'Data deve estar no formato DD/MM/YYYY')
    .test('data-valida', 'Data inválida', value => {
      if (!value) return false
      const data = new Date(formatarDataISO(value))
      return !isNaN(data.getTime()) // garante que seja uma data válida
    })
    .test('data-futura', 'A data não pode ser anterior a hoje', value => {
      if (!value) return false
      const data = new Date(formatarDataISO(value))
      const hoje = new Date()
      hoje.setHours(0, 0, 0, 0)
      return data >= hoje
    }),
})
