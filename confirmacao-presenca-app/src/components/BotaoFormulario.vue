<template>
  <button
    :style="{ backgroundColor: disabled ? 'var(--cor-cinza)' : cor }"
    class="botao-formulario"
    type="submit"
    :disabled="disabled"
  >
    {{ texto }}
  </button>
</template>

<script setup>
defineProps({
  texto: {
    type: String,
    required: true,
  },
  cor: {
    type: String,
    default: 'var(--cor-primaria)',
  },
  disabled: {
    type: Boolean,
    default: false,
  },
})
</script>

<style scoped>
.botao-formulario {
  color: white;
  border: none;
  border-radius: 0.4rem;
  cursor: pointer;
  padding-top: 1.4rem;
  padding-bottom: 1.4rem;
  font-size: 1.8rem;
  font-weight: 400;
}
</style>
