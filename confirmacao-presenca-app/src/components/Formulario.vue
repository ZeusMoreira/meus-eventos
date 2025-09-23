<template>
  <div class="formulario">
    <h2 class="formulario__titulo">{{ titulo }}</h2>
    <Form :validation-schema="schema" @submit="confirmar" v-slot="{ meta }">
      <div class="campos">
        <slot name="campos"></slot>
      </div>
      <botao-formulario :disabled="meta.dirty && !meta.valid" :texto="textoBotao" />
    </Form>
  </div>
</template>

<script setup>
import BotaoFormulario from '@/components/BotaoFormulario.vue'
import { Form } from 'vee-validate'

const emit = defineEmits(['submeter'])

defineProps({
  titulo: {
    type: String,
    default: 'Insira os dados abaixo',
  },
  schema: {
    type: Object,
  },
  textoBotao: {
    type: String,
    default: 'Enviar',
  },
})

const confirmar = (valores) => {
  emit('submeter', {
    ...valores,
  })
}
</script>

<style scoped>
.campos {
  display: flex;
  flex-direction: column;
  gap: 2.8rem;
}

form {
  display: flex;
  flex-direction: column;
  gap: 2.8rem;
}

.formulario {
  display: flex;
  flex-direction: column;
  gap: 4.2rem;
}

.formulario__titulo {
  text-transform: uppercase;
  font-weight: 500;
  font-size: 1.6rem;
  color: var(--cor-primaria);
}
</style>
