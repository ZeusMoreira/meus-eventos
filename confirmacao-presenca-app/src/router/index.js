import { createRouter, createWebHistory } from 'vue-router'
import ConfirmacaoPresenca from '../pages/area-publica/ConfirmacaoPresenca.vue'
import LoginAdmin from '../pages/area-publica/LoginAdmin.vue'
import AreaAdministrativa from '../pages/area-administrativa/AreaAdministrativa.vue'
import { useAuthStore } from '@/stores/auth.js'
import PaginaNaoEncontrada from '@/pages/area-publica/PaginaNaoEncontrada.vue'
import { jwtDecode } from 'jwt-decode'
import ResetSenha from '@/pages/area-publica/ResetSenha.vue'
import GerenciamentoEventos from '@/pages/evento/GerenciamentoEventos.vue'
import QrCode from '@/pages/area-publica/QrCode.vue'
import { useNavegacaoStore } from '@/stores/navegacao'
import Evento from '@/pages/evento/Evento.vue'
import AdicaoEvento from '@/pages/evento/AdicaoEvento.vue'
import AdicaoParticipante from "@/pages/participante/AdicaoParticipante.vue";

const routes = [
  { path: '/home/:codigo', component: ConfirmacaoPresenca },
  { path: '/home/login', component: LoginAdmin },
  { path: '/home/admin', component: AreaAdministrativa },
  { path: '/home/admin/gerenciamento', component: GerenciamentoEventos },
  { path: '/home/admin/eventos/criacao', component: AdicaoEvento },
  { path: '/home/admin/eventos/:codigo/adicao-participante', component: AdicaoParticipante },
  { path: '/home/admin/eventos/:codigo', component: Evento },
  { path: '/home/admin/eventos/:codigo/qr-code', component: QrCode },
  { path: '/resetar-senha', component: ResetSenha },
  {
    path: '/',
    redirect: (to) => {
      const auth = useAuthStore()
      return auth.isAuthenticated() ? '/home/admin' : '/home/login'
    },
  },
  {
    path: '/home',
    redirect: (to) => {
      const auth = useAuthStore()
      return auth.isAuthenticated() ? '/home/admin' : '/home/login'
    },
  },
  { path: '/:pathMatch(.*)*', name: 'NotFound', component: PaginaNaoEncontrada },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to, from, next) => {
  const nav = useNavegacaoStore()

  if (from.fullPath && from.fullPath !== '/' && from.fullPath !== to.fullPath) {
    nav.setUltimaRota(from.fullPath)
  }

  const auth = useAuthStore()
  const token = auth.token

  if (token) {
    try {
      const decoded = jwtDecode(token)
      const now = Date.now() / 1000
      if (decoded.exp && decoded.exp < now) {
        auth.logout()
        return next('/home/login')
      }
    } catch (e) {
      auth.logout()
      return next('/home/login')
    }
  }

  if (to.path.startsWith('/home/admin') && !auth.isAuthenticated()) {
    return next('/home/login')
  }

  if (to.path.startsWith('/home/login') && auth.isAuthenticated()) {
    return next('/home/admin')
  }

  next()
})

export default router
