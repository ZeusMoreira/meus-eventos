<template>
  <div v-if="exibir" class="modal-overlay" @click="emitirFechar">
    <div class="modal" @click.stop>
      <h2 class="modal-titulo">{{ titulo }}</h2>
      <div class="modal-conteudo">
        <slot>{{ conteudo }}</slot>
      </div>
      <div class="modal-botoes">
        <button class="modal-botao" @click="emitirConfirmar">Confirmar</button>
        <button class="modal-botao__outlined" @click="emitirFechar">Fechar</button>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  exibir: { type: Boolean, required: true },
  titulo: { type: String, default: 'Modal' },
  conteudo: { type: String, default: '' },
})

const emit = defineEmits(['fechar', 'confirmar'])

function emitirFechar() {
  emit('fechar')
}

function emitirConfirmar() {
  emit('confirmar')
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1;
}

.modal {
  background: var(--cor-branco);
  padding: 2rem;
  border-radius: 0.8rem;
  max-width: 400px;
  width: 90%;
  text-align: center;
}

.modal-titulo {
  margin-bottom: 3rem;
  font-size: 1.8rem;
  font-weight: 600;
  color: var(--cor-primaria);
}

.modal-conteudo {
  font-size: 1.6rem;
}

.modal-botao {
  margin-top: 3rem;
  background: var(--cor-primaria, #007bff);
  color: white;
  border: none;
  border-radius: 0.4rem;
  padding: 0.8rem 1.4rem;
  cursor: pointer;
  font-size: 1.4rem;
}

.modal-botao__outlined {
  margin-top: 3rem;
  background: var(--cor-branco);
  color: var(--cor-primaria);
  border: 1px solid var(--cor-primaria);
  border-radius: 0.4rem;
  padding: 0.8rem 1.4rem;
  cursor: pointer;
  font-size: 1.4rem;
}

.modal-botoes {
  display: flex;
  justify-content: space-between;
}
</style>
