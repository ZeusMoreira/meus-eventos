export function useEmail() {
  function validarEmail(email) {
    if (!email) return false

    const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    return regexEmail.test(email)
  }

  return { validarEmail }
}
