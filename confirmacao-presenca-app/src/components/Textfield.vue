<template>
  <div class="campo">
    <div class="input-wrapper">
      <i
        v-if="iconLeft"
        :style="{ color: props.fontColor }"
        :class="['mdi', iconLeft, 'input-icon-left']"
        @click="emitirEventoIconLeft"
      />
      <label v-if="exibirLabel" :class="{ 'erro-label': errorMessage }">{{ label }}</label>
      <input
        ref="inputRef"
        v-bind="field"
        :type="type"
        :placeholder="placeholder"
        :class="{ 'erro-input': errorMessage }"
        :style="inputStyle"
        :maxlength="ehCpf ? 14 : null"
        v-imask="mascara"
        @accept="onAccept"
        @input="onInput"
        @change="onChange"
      />
      <i
        v-if="iconRight"
        :style="{ color: props.fontColor }"
        :class="['mdi', iconRight, 'input-icon-right']"
        @click="emitirEventoIconRight"
      />
    </div>
    <span v-if="errorMessage" class="mensagem-erro">{{ errorMessage }}</span>
  </div>
</template>

<script setup>
import { useField } from 'vee-validate'
import { IMaskDirective } from 'vue-imask'
import {computed, ref} from 'vue'

const inputRef = ref(null)

defineExpose({
  limpar: () => {
    handleChange('')
    if (inputRef.value) {
      inputRef.value.value = ''
    }
  }
})

defineOptions({
  directives: {
    imask: IMaskDirective,
  },
})

const props = defineProps({
  name: { type: String, required: true },
  label: String,
  type: { type: String, default: 'text' },
  mascara: { type: [Object, null], default: null },
  ehCpf: { type: Boolean, default: false },
  placeholder: String,
  iconLeft: { type: String, default: null },
  iconRight: { type: String, default: null },
  exibirLabel: { type: Boolean, default: true },
  borderColor: { type: String, default: 'var(--cinza-campos)' },
  fontColor: { type: String, default: 'var(--cinza-campos)' },
})

const { field, errorMessage, handleChange } = useField(props.name)

const inputStyle = computed(() => {
  return {
    paddingLeft: props.iconLeft ? '4rem' : '1.2rem',
    paddingRight: props.iconRight ? '4rem' : '1.2rem',
    borderColor: props.borderColor,
    color: props.fontColor,
  }
})

const emit = defineEmits(['clickIconRight', 'clickIconLeft', 'alteracaoCampo'])

function emitirEventoIconRight() {
  emit('clickIconRight')
}

function emitirEventoIconLeft() {
  emit('clickIconLeft')
}
function onAccept(e) {
  if (props.ehCpf) {
    const valorLimpo = e.detail.value.replace(/\D/g, '')
    handleChange(valorLimpo)
  } else {
    handleChange(e.detail.value)
  }
}

function onInput(e) {
  if (props.ehCpf) {
    const valorLimpo = e.target.value.replace(/\D/g, '')
    handleChange(valorLimpo)
  } else {
    handleChange(e.target.value)
  }
}

function onChange(e) {
  if (props.ehCpf) {
    const valorLimpo = e.target.value.replace(/\D/g, '')
    emit('alteracaoCampo', valorLimpo)
  } else {
    emit('alteracaoCampo', e.target.value)
  }
}

</script>

<style scoped>
.campo {
  display: flex;
  flex-direction: column;
  align-items: start;
}

.campo label {
  background-color: var(--cor-branco);
  position: relative;
  top: 0.5rem;
  font-size: 1.2rem;
  color: var(--cinza-campos);
  margin-left: 1rem;
  padding: 0 0.3rem;
  transition: color 0.3s ease;
}

.input-wrapper input {
  width: 100%;
  padding: 1.2rem;
  font-size: 1.6rem;
  border: 1px solid;
  border-radius: 0.4rem;
  outline: none;
  transition: border-color 0.3s ease;
}

.campo input[type='password'] {
  font-size: 1.4rem;
  font-family: Verdana, sans-serif;
  letter-spacing: 0.2rem;
  padding-top: 1.4rem;
  padding-bottom: 1.4rem;
}

.campo input[type='password']::placeholder {
  font-family: 'Jost', sans-serif;
  letter-spacing: normal;
  font-size: 1.6rem;
}

.erro-label {
  color: red !important;
}

.erro-input {
  border-color: red !important;
}

.mensagem-erro {
  color: red;
  font-size: 1.2rem;
  margin-top: 0.4rem;
}

.input-wrapper {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: start;
  width: 100%;
}

.input-icon-left {
  position: absolute;
  top: 50%;
  left: 1.2rem;
  transform: translateY(-50%);
  font-size: 2.2rem;
}

.input-icon-right {
  position: absolute;
  top: calc(50% + 0.6rem);
  right: 1.2rem;
  transform: translateY(-50%);
  font-size: 2.2rem;
  cursor: pointer;
}
</style>
