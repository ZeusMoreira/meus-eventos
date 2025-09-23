import * as yup from 'yup'
import { useEmail } from '@/composables/useEmail.js'

const { validarEmail } = useEmail()

export const schemaLoginAdmin = yup.object({
  email: yup
    .string()
    .required('O Email é obrigatório')
    .test('email-valido', 'Email inválido', (value) => validarEmail(value)),
  senha: yup.string().required('A senha é obrigatória'),
})
