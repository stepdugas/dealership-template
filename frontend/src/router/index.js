import { createRouter, createWebHistory } from 'vue-router'

/**
 * Application routes.
 * Admin routes use a lazy-loaded chunk to keep the main bundle small.
 * Optional scaffold pages are included but only render placeholder content.
 */
const routes = [
  // ── Public pages ────────────────────────────────────────────────────
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/HomeView.vue'),
    meta: { title: 'Home' },
  },
  {
    path: '/inventory',
    name: 'Inventory',
    component: () => import('../views/InventoryView.vue'),
    meta: { title: 'Browse Inventory' },
  },
  {
    path: '/inventory/:id',
    name: 'CarDetail',
    component: () => import('../views/CarDetailView.vue'),
    meta: { title: 'Vehicle Details' },
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/AboutView.vue'),
    meta: { title: 'About Us' },
  },
  {
    path: '/contact',
    name: 'Contact',
    component: () => import('../views/ContactView.vue'),
    meta: { title: 'Contact Us' },
  },
  {
    path: '/intake',
    name: 'ClientIntake',
    component: () => import('../views/ClientIntakeForm.vue'),
    meta: { title: 'Get Your Site Online' },
  },

  // ── Optional scaffold pages (route + placeholder component) ──────────
  {
    path: '/staff',
    name: 'Staff',
    component: () => import('../views/StaffView.vue'),
    meta: { title: 'Meet the Staff' },
  },
  {
    path: '/financing',
    name: 'Financing',
    component: () => import('../views/FinancingView.vue'),
    meta: { title: 'Financing' },
  },
  {
    path: '/sell-your-car',
    name: 'SellYourCar',
    component: () => import('../views/SellYourCarView.vue'),
    meta: { title: 'Sell Us Your Car' },
  },
  {
    path: '/faq',
    name: 'FAQ',
    component: () => import('../views/FaqView.vue'),
    meta: { title: 'FAQ' },
  },
  {
    path: '/blog',
    name: 'Blog',
    component: () => import('../views/BlogView.vue'),
    meta: { title: 'Blog' },
  },

  // ── Manager panel ────────────────────────────────────────────────────
  {
    path: '/manager/login',
    name: 'ManagerLogin',
    component: () => import('../views/manager/ManagerLoginView.vue'),
    meta: { title: 'Manager Login', isManagerPublic: true },
  },
  {
    path: '/manager',
    name: 'ManagerDashboard',
    component: () => import('../views/manager/ManagerDashboardView.vue'),
    meta: { title: 'Manager Dashboard', requiresManager: true },
  },

  // ── Admin panel ──────────────────────────────────────────────────────
  {
    path: '/admin/login',
    name: 'AdminLogin',
    component: () => import('../views/admin/AdminLoginView.vue'),
    meta: { title: 'Admin Login', isAdminPublic: true },
  },
  {
    path: '/admin',
    name: 'AdminDashboard',
    component: () => import('../views/admin/AdminDashboardView.vue'),
    meta: { title: 'Admin Dashboard', requiresAdmin: true },
    redirect: '/admin/cars',
    children: [
      {
        path: 'cars',
        name: 'AdminCars',
        component: () => import('../views/admin/AdminCarsView.vue'),
        meta: { title: 'Manage Inventory', requiresAdmin: true },
      },
      {
        path: 'cars/new',
        name: 'AdminCarNew',
        component: () => import('../views/admin/AdminCarFormView.vue'),
        meta: { title: 'Add Vehicle', requiresAdmin: true },
      },
      {
        path: 'cars/:id/edit',
        name: 'AdminCarEdit',
        component: () => import('../views/admin/AdminCarFormView.vue'),
        meta: { title: 'Edit Vehicle', requiresAdmin: true },
      },
      {
        path: 'contacts',
        name: 'AdminContacts',
        component: () => import('../views/admin/AdminContactsView.vue'),
        meta: { title: 'Contact Submissions', requiresAdmin: true },
      },
    ],
  },

  // ── 404 catch-all ────────────────────────────────────────────────────
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('../views/NotFoundView.vue'),
    meta: { title: 'Page Not Found' },
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
  // Scroll to top on every route change
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) return savedPosition
    if (to.hash) return { el: to.hash, behavior: 'smooth' }
    return { top: 0, behavior: 'smooth' }
  },
})

// ── Navigation guard: protect admin routes ──────────────────────────────
router.beforeEach((to, from, next) => {
  // Update document title
  const { DEALERSHIP_NAME } = /* will be tree-shaken */ { DEALERSHIP_NAME: '' }
  if (to.meta.title) {
    document.title = to.meta.title
  }

  // Admin auth check
  if (to.meta.requiresAdmin) {
    const isLoggedIn = localStorage.getItem('admin_token')
    if (!isLoggedIn) {
      return next({ name: 'AdminLogin', query: { redirect: to.fullPath } })
    }
  }

  // Manager auth check
  if (to.meta.requiresManager) {
    const isLoggedIn = localStorage.getItem('manager_token')
    if (!isLoggedIn) {
      return next({ name: 'ManagerLogin', query: { redirect: to.fullPath } })
    }
  }

  next()
})

export default router
