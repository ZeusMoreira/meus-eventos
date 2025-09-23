<template>
  <div class="pagina-404">
    <div class="pagina-404-texto">
      <h1>404</h1>
      <p>Página não encontrada</p>
    </div>
    <router-link to="/">Voltar para a página inicial</router-link>
  </div>
</template>

<script setup></script>

<style scoped>
.pagina-404 {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  font-size: 1.6rem;
  gap: 4rem;
}

.pagina-404-texto {
  display: flex;
  flex-direction: column;
  align-items: center;
  color: var(--cor-primaria);
  font-size: 2.4rem;
  gap: 1rem;
}

.pagina-404-texto > h1:first-child {
  font-weight: bold;
  font-size: 3.2rem;
}
</style>
