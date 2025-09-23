<template>
  <div
    class="container-wrapper"
    :style="{
      '--cor-habilitado': corHabilitado,
      '--cor-desabilitado': corDesabilitado,
    }"
  >
    <div class="paginacao-wrapper">
      <i :class="[iconLeft, setaEsquerda, 'seta']" @click="paginar('esquerda')" />
      <template v-for="pagina in totalPaginas" :key="pagina">
        <span class="desabilitado" v-if="pagina - 1 === paginaAtual">{{ pagina }}</span>
        <span
          class="desabilitado elipse"
          v-else-if="pagina - 1 === paginaAtual + 2 && pagina - 1 !== totalPaginas - 1"
        >
          ...
        </span>
        <span
          class="desabilitado elipse"
          v-else-if="pagina - 1 === paginaAtual - 2 && pagina - 1 !== 0"
        >
          ...
        </span>
        <span
          class="habilitado"
          v-else-if="deveExibir(pagina)"
          @click="selecionarPagina(pagina - 1)"
          >{{ pagina }}</span
        >
      </template>
      <i :class="[iconRight, setaDireita, 'seta']" @click="paginar('direita')" />
    </div>
    <span class="total">{{ props.numeroDeElementos }} de {{ totalElements }}</span>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  totalPaginas: { type: Number, required: true },
  totalElements: { type: Number, required: true },
  paginaAtual: { type: Number, required: true },
  numeroDeElementos: { type: Number, required: true },
  iconLeft: { type: String, default: 'mdi mdi-chevron-left' },
  iconRight: { type: String, default: 'mdi mdi-chevron-right' },
  corDesabilitado: { type: String, default: 'var(--cor-cinza)' },
  corHabilitado: { type: String, default: 'var(--cor-primaria)' },
})

const setaEsquerda = computed(() => {
  if (props.paginaAtual === 0) {
    return 'desabilitado'
  } else {
    return 'habilitado'
  }
})

const setaDireita = computed(() => {
  if (props.paginaAtual === props.totalPaginas - 1) {
    return 'desabilitado'
  } else {
    return 'habilitado'
  }
})

const emit = defineEmits(['selecionarPagina', 'paginar'])
function selecionarPagina(pagina) {
  emit('selecionarPagina', pagina)
}

function paginar(direcao) {
  if (direcao === 'esquerda' && setaEsquerda.value === 'desabilitado') {
    return
  }

  if (direcao === 'direita' && setaDireita.value === 'desabilitado') {
    return
  }
  emit('paginar', direcao)
}

function deveExibir(pagina) {
  if (pagina - 1 > props.paginaAtual + 2 && pagina - 1 !== props.totalPaginas - 1) {
    return false
  }

  if (pagina - 1 < props.paginaAtual - 2 && pagina - 1 !== 0) {
    return false
  }

  return true
}
</script>

<style scoped>
.paginacao-wrapper {
  display: flex;
  align-items: center;
  gap: 1rem;
  font-size: 1.4rem;
  font-weight: bold;
}

.desabilitado {
  color: var(--cor-desabilitado);
  cursor: pointer;
}

.habilitado {
  color: var(--cor-habilitado);
  cursor: pointer;
}

.elipse {
  cursor: default;
}

.seta {
  font-size: 2rem;
  padding-top: 0.25rem;
}

.container-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.total {
  color: var(--cor-habilitado);
  font-size: 1.4rem;
}
</style>
