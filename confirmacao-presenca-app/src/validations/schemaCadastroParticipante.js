import * as yup from 'yup'
import { useCpf } from '@/composables/useCpf.js'

const { validarCpf } = useCpf()

export const schemaCadastroParticipante = yup.object({
  cpf: yup
    .string()
    .required('O CPF é obrigatório')
    .length(11, 'CPF deve ter 11 dígitos')
    .test('cpf-valido', 'CPF inválido', (value) => validarCpf(value)),
  nome: yup.string().required('O nome é obrigatório').min(3, 'Mínimo de 3 caracteres'),
})
