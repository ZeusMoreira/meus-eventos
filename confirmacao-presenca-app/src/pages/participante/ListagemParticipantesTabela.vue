<template>
  <modal-ver-mais
    :exibir="modalAberto"
    titulo="Nome do Participante"
    :conteudo="participanteSelecionado"
    @fechar="fecharModal"
  />
  <div class="listagem">
    <table class="tabela" v-if="participantes.length > 0">
      <tr class="cabecalho">
        <th class="cabecalho__nome">Nome</th>
        <th class="cabecalho__cpf">CPF</th>
        <th class="cabecalho__acoes">Ações</th>
      </tr>
      <tr class="linha-tabela" v-for="(participante, index) in participantes" :key="index">
        <td class="nome">{{ participante.nome }}</td>
        <td class="cpf">{{ formatarCpf(participante.cpf) }}</td>
        <td class="acao">
          <i
            class="mdi mdi-delete icon"
            @click="emitirEventoExcluirParticipante(participante.id)"
          />
        </td>
      </tr>
    </table>
    <div v-else class="no-data">
      <span class="texto-vazio"
        >Não há partipantes.<br />
        Que tal adicionar um hein ?
      </span>
      <i class="mdi mdi-emoticon-outline icone-vazio"></i>
      <span>Clique em <b>Adicionar Partipante</b>.</span>
    </div>
  </div>
</template>

<script setup>
import ModalVerMais from '@/components/ModalVerMais.vue'
import { ref } from 'vue'
import { useCpf } from '@/composables/useCpf.js'

const { formatarCpf } = useCpf()
const modalAberto = ref(false)
const participanteSelecionado = ref('')

defineProps({
  participantes: {
    type: Array,
    required: true,
  },
})

const emit = defineEmits(['excluir-participante'])

function emitirEventoExcluirParticipante(idParticipante) {
  emit('excluir-participante', idParticipante)
}

function abrirModal(titulo) {
  participanteSelecionado.value = titulo
  modalAberto.value = true
}

function fecharModal() {
  participanteSelecionado.value = ''
  modalAberto.value = false
}
</script>

<style scoped>
.tabela {
  font-size: 1.2rem;
  color: var(--preto-tabela);
  border-radius: 0.4rem;
  border: 1px solid var(--preto-tabela);
  border-collapse: collapse;
  display: flex;
  flex-direction: column;
}

tr {
  width: 100%;
  display: flex;
}

th {
  font-weight: bold;
}

td {
  display: flex;
  align-items: center;
  font-weight: 500;
}

.cabecalho__nome {
  border-top-left-radius: 0.4rem;
}

.cabecalho__nome,
.nome {
  padding: 1.2rem;
  text-align: start;
  width: 40%;
}

.cabecalho__cpf,
.cpf {
  padding: 1.2rem;
  text-align: start;
  width: 40%;
}

.cabecalho__acoes,
.acao {
  display: table-cell;
  padding: 1.2rem;
  text-align: center;
  width: 20%;
}

.linha-tabela {
  border-top: 1px solid var(--preto-tabela);
}

.no-data {
  display: flex;
  flex-direction: column;
  gap: 2rem;
  color: var(--cinza-campos);
  font-size: 1.6rem;
  margin-top: 4rem;
  margin-bottom: 4rem;
}

.icone-vazio {
  font-size: 8rem;
}

.texto-vazio {
  line-height: 1.4;
}

b {
  font-weight: bold;
}

.icon {
  font-size: 1.6rem;
}
</style>
