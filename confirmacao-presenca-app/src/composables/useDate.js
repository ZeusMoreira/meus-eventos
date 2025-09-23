export function useDate() {
  function formatarData(data) {
    if (!data) return ''
    const d = new Date(data)
    return d.toLocaleDateString('pt-BR', { timeZone: 'UTC' })
  }

  function formatarDataISO(dataBr) {
    if (!dataBr) return ''
    const [dia, mes, ano] = dataBr.split('/')
    return `${ano}-${mes}-${dia}`
  }

  return { formatarData, formatarDataISO }
}
