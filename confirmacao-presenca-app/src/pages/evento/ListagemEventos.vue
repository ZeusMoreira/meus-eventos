<template>
  <modal-ver-mais
    :exibir="modalAberto"
    titulo="Nome do Evento"
    :conteudo="eventoSelecionado"
    @fechar="fecharModal"
  />
  <div class="listagem">
    <ul class="listagem__lista" v-if="eventos.length > 0">
      <li class="listagem__lista__card" v-for="(evento, index) in eventos" :key="index">
        <div class="card__evento-infos">
          <h3 class="evento-titulo" @click="abrirModal(evento.titulo)">
            {{ evento.titulo }}
          </h3>
          <h4 class="evento-data">{{ formatarData(evento.dataEvento) }}</h4>
        </div>
        <div class="card__evento-acoes">
          <i class="mdi mdi-qrcode card-icone" @click="emitirEventoGerarQrCode(evento)"></i>
          <i class="mdi mdi-login card-icone" @click="acessarEvento(evento)"></i>
        </div>
      </li>
    </ul>
    <div v-else class="no-data">
      <span class="texto-vazio"
        >Não há eventos adicionados.<br />
        Que tal criar um hein ?
      </span>
      <i class="mdi mdi-emoticon-outline icone-vazio"></i>
      <span>Clique em <b>Adicionar Eventos</b>.</span>
    </div>
  </div>
</template>

<script setup>
import ModalVerMais from '@/components/ModalVerMais.vue'
import { ref } from 'vue'
import { useDate } from '@/composables/useDate.js'

const { formatarData } = useDate()
const modalAberto = ref(false)
const eventoSelecionado = ref('')

defineProps({
  eventos: {
    type: Array,
    required: true,
  },
})

const emit = defineEmits(['gerar-qr-code', 'acessar-evento'])

function emitirEventoGerarQrCode(evento) {
  emit('gerar-qr-code', evento)
}

function acessarEvento(evento) {
  emit('acessar-evento', evento)
}

function abrirModal(titulo) {
  eventoSelecionado.value = titulo
  modalAberto.value = true
}

function fecharModal() {
  eventoSelecionado.value = ''
  modalAberto.value = false
}
</script>

<style scoped>
.listagem {
  display: flex;
  flex-direction: column;
  gap: 2.4rem;
  color: var(--cor-primaria);
}

.listagem__lista {
  display: flex;
  flex-direction: column;
  gap: 1.6rem;
}

.listagem__lista__card {
  display: flex;
  justify-content: space-between;
  background-color: var(--cor-primaria);
  color: var(--cor-branco);
  border-radius: 0.4rem;
  padding: 1.7rem;
  align-items: center;
}

.evento-titulo {
  font-size: 1.6rem;
  font-weight: bold;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 180px;
  cursor: pointer;
}

.evento-data {
  font-size: 1.3rem;
  font-weight: normal;
}

.card__evento-infos {
  display: flex;
  flex-direction: column;
  align-items: start;
  gap: 0.5rem;
}

i {
  font-size: 2.4rem;
}

.card__evento-acoes {
  display: flex;
  align-items: center;
  gap: 1rem;
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
</style>
