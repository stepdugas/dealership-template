/**
 * API service layer — all backend calls go through here.
 * Base URL is configured in config.js / .env
 */
import axios from 'axios'
import { API_BASE_URL } from '../config'

const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: { 'Content-Type': 'application/json' },
})

// ── Request interceptor: attach admin token if present ──────────────────
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('admin_token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// ── Response interceptor: redirect to login on 401 ─────────────────────
api.interceptors.response.use(
  (res) => res,
  (err) => {
    if (err.response?.status === 401) {
      localStorage.removeItem('admin_token')
      window.location.href = '/admin/login'
    }
    return Promise.reject(err)
  },
)

// ── Inventory endpoints ─────────────────────────────────────────────────

/**
 * Fetch paginated/filtered inventory.
 * @param {Object} filters - { make, model, year, minPrice, maxPrice, maxMileage, page, size }
 */
export const getInventory = (filters = {}) =>
  api.get('/api/inventory', { params: filters })

/**
 * Fetch a single vehicle by id.
 * @param {number|string} id
 */
export const getCar = (id) => api.get(`/api/inventory/${id}`)

/**
 * Create a new vehicle (admin only).
 * @param {Object} carData
 */
export const createCar = (carData) => api.post('/api/inventory', carData)

/**
 * Update an existing vehicle (admin only).
 * @param {number|string} id
 * @param {Object} carData
 */
export const updateCar = (id, carData) => api.put(`/api/inventory/${id}`, carData)

/**
 * Delete a vehicle (admin only).
 * @param {number|string} id
 */
export const deleteCar = (id) => api.delete(`/api/inventory/${id}`)

// ── Contact endpoint ────────────────────────────────────────────────────

/**
 * Submit a contact form.
 * @param {{ name, email, phone, message, carId? }} payload
 */
export const submitContact = (payload) => api.post('/api/contact', payload)

// ── Admin auth endpoint ─────────────────────────────────────────────────

/**
 * Admin login — returns a JWT token on success.
 * @param {{ username, password }} credentials
 */
export const adminLogin = (credentials) => api.post('/api/admin/login', credentials)

// ── Contact submissions (admin) ─────────────────────────────────────────

/** Fetch all contact submissions (admin only). */
export const getContactSubmissions = () => api.get('/api/admin/contacts')

export default api
