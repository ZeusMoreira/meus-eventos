<template>
  <button
    :style="{ backgroundColor: disabled ? 'var(--cor-cinza)' : backgroundColor, color: cor }"
    class="botao"
    type="submit"
    :disabled="disabled"
    @click="tratarEventoClick"
  >
    <i v-if="icon" :class="icon" style="align-self: start"></i>
    <span>{{ texto }}</span>
    <span class="espaco"></span>
  </button>
</template>

<script setup>
defineProps({
  texto: {
    type: String,
    required: true,
  },
  backgroundColor: {
    type: String,
    default: 'var(--cor-branco)',
  },
  cor: {
    type: String,
    default: 'var(--cor-primaria)',
  },
  disabled: {
    type: Boolean,
    default: false,
  },
  icon: {
    type: String,
    default: '',
  },
})

const emit = defineEmits(['clicar'])

function tratarEventoClick() {
  emit('clicar')
}
</script>

<style scoped>
.botao {
  display: flex;
  justify-content: space-around;
  align-items: center;
  color: white;
  border: none;
  border-radius: 0.4rem;
  cursor: pointer;
  padding-top: 0.5rem;
  padding-bottom: 0.5rem;
  font-size: 2rem;
  font-weight: 400;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
}

.botao i {
  font-size: 4rem;
}

.botao .espaco {
  width: 2.4rem;
}
</style>
