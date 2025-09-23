export function useCpf() {
  function validarCpf(cpf) {
    if (!cpf) return false
    cpf = cpf.replace(/\D/g, '')
    if (cpf.length !== 11 || /^(\d)\1+$/.test(cpf)) return false

    let soma = 0
    let resto

    for (let i = 1; i <= 9; i++) soma += parseInt(cpf.substring(i - 1, i)) * (11 - i)
    resto = (soma * 10) % 11
    if (resto === 10 || resto === 11) resto = 0
    if (resto !== parseInt(cpf.substring(9, 10))) return false

    soma = 0
    for (let i = 1; i <= 10; i++) soma += parseInt(cpf.substring(i - 1, i)) * (12 - i)
    resto = (soma * 10) % 11
    if (resto === 10 || resto === 11) resto = 0
    if (resto !== parseInt(cpf.substring(10, 11))) return false

    return true
  }

  function formatarCpf(cpf) {
    if (!cpf) return ''
    cpf = cpf.replace(/\D/g, '')
    if (cpf.length !== 11) return cpf

    return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4')
  }

  return { validarCpf, formatarCpf }
}
