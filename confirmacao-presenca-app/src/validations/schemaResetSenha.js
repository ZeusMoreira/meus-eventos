import * as yup from 'yup'

export const schemaResetSenha = yup.object({
  senha: yup.string().required('A senha é obrigatória'),
  confirmarSenha: yup.string().required('A confirmação da senha é obrigatória'),
})
