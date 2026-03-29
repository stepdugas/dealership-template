<!--
  FinancingView — Fully customizable financing page template.

  Customization points are marked with [BRACKET] placeholders matching config.js
  conventions. Replace static text in the sections below for each client.
-->
<template>
  <PageLayout>

    <!-- ── Hero banner ───────────────────────────────────────────────── -->
    <div class="bg-dark-800 pt-28 pb-14">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <h1 class="text-4xl font-extrabold text-white" data-aos="fade-up">
          <!-- [FINANCING_HEADLINE] -->
          Flexible Financing for Every Driver
        </h1>
        <p class="text-gray-400 mt-3 max-w-2xl text-lg" data-aos="fade-up" data-aos-delay="100">
          <!-- [FINANCING_SUBHEADLINE] -->
          We work with top lenders to find the best rate for your situation —
          all credit types welcome. Get pre-approved in minutes.
        </p>
        <div class="mt-8 flex flex-wrap gap-4" data-aos="fade-up" data-aos-delay="200">
          <a :href="'tel:' + PHONE" class="btn-primary text-base px-8 py-3">
            Call to Apply — {{ PHONE }}
          </a>
          <RouterLink to="/contact" class="btn-secondary text-base px-8 py-3">
            Send Us a Message
          </RouterLink>
        </div>
      </div>
    </div>

    <!-- ── Why finance with us ────────────────────────────────────────── -->
    <section class="py-16 bg-white">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <h2 class="text-2xl font-bold text-gray-900 mb-10 text-center" data-aos="fade-up">
          <!-- [WHY_FINANCE_HEADLINE] -->
          Why Finance With Us?
        </h2>
        <div class="grid md:grid-cols-3 gap-8">
          <div v-for="benefit in benefits" :key="benefit.title"
               class="text-center p-6 rounded-2xl border border-gray-100 shadow-sm"
               data-aos="fade-up">
            <div class="w-12 h-12 bg-primary-100 rounded-xl flex items-center justify-center mx-auto mb-4">
              <component :is="benefit.icon" class="w-6 h-6 text-primary-600" />
            </div>
            <h3 class="font-bold text-gray-900 mb-2">{{ benefit.title }}</h3>
            <p class="text-gray-500 text-sm leading-relaxed">{{ benefit.description }}</p>
          </div>
        </div>
      </div>
    </section>

    <!-- ── Loan calculator ───────────────────────────────────────────── -->
    <section class="py-16 bg-gray-50">
      <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
        <h2 class="text-2xl font-bold text-gray-900 mb-2 text-center" data-aos="fade-up">
          Payment Estimator
        </h2>
        <p class="text-gray-500 text-center mb-10 text-sm" data-aos="fade-up" data-aos-delay="50">
          Estimate your monthly payment. Actual rates depend on credit and lender approval.
        </p>

        <div class="bg-white rounded-2xl shadow-sm border border-gray-100 p-8" data-aos="fade-up">
          <div class="grid sm:grid-cols-2 gap-6 mb-6">
            <div>
              <label class="form-label">Vehicle Price ($)</label>
              <input v-model.number="calc.price" type="number" min="0" step="500"
                     class="form-input" placeholder="25000" />
            </div>
            <div>
              <label class="form-label">Down Payment ($)</label>
              <input v-model.number="calc.down" type="number" min="0" step="500"
                     class="form-input" placeholder="3000" />
            </div>
            <div>
              <label class="form-label">Annual Interest Rate (%)</label>
              <input v-model.number="calc.rate" type="number" min="0" max="30" step="0.1"
                     class="form-input" placeholder="6.9" />
            </div>
            <div>
              <label class="form-label">Loan Term (months)</label>
              <select v-model.number="calc.term" class="form-input">
                <option :value="24">24 months (2 yr)</option>
                <option :value="36">36 months (3 yr)</option>
                <option :value="48">48 months (4 yr)</option>
                <option :value="60">60 months (5 yr)</option>
                <option :value="72">72 months (6 yr)</option>
                <option :value="84">84 months (7 yr)</option>
              </select>
            </div>
          </div>

          <!-- Result -->
          <div class="bg-primary-50 rounded-xl p-6 text-center border border-primary-100">
            <p class="text-sm text-gray-500 mb-1">Estimated Monthly Payment</p>
            <p class="text-4xl font-extrabold text-primary-700">
              {{ monthlyPayment !== null ? '$' + monthlyPayment.toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 }) : '—' }}
            </p>
            <p class="text-xs text-gray-400 mt-2">
              Total loan: {{ loanAmount > 0 ? '$' + loanAmount.toLocaleString() : '—' }} &nbsp;|&nbsp;
              Total paid: {{ totalPaid > 0 ? '$' + totalPaid.toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 }) : '—' }}
            </p>
          </div>

          <p class="text-xs text-gray-400 mt-4 text-center">
            This calculator is for estimation purposes only. Taxes, fees, and final rates vary.
          </p>
        </div>
      </div>
    </section>

    <!-- ── Financing partners / lenders ─────────────────────────────── -->
    <section class="py-16 bg-white">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <h2 class="text-2xl font-bold text-gray-900 mb-3 text-center" data-aos="fade-up">
          <!-- [LENDERS_HEADLINE] -->
          Our Lending Partners
        </h2>
        <p class="text-gray-500 text-center mb-10 max-w-xl mx-auto text-sm" data-aos="fade-up" data-aos-delay="50">
          <!-- [LENDERS_SUBTEXT] -->
          We partner with a network of local and national lenders to secure competitive
          rates for buyers at every credit level.
        </p>

        <!-- [LENDERS_GRID] Replace these placeholder cards with actual lender logos/names -->
        <div class="grid sm:grid-cols-2 lg:grid-cols-4 gap-6">
          <div v-for="lender in lenders" :key="lender.name"
               class="border border-gray-100 rounded-2xl p-6 text-center shadow-sm hover:shadow-md transition-shadow"
               data-aos="fade-up">
            <div class="w-12 h-12 bg-gray-100 rounded-xl flex items-center justify-center mx-auto mb-3">
              <svg class="w-6 h-6 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M3 10h18M7 15h1m4 0h1m-7 4h12a3 3 0 003-3V8a3 3 0 00-3-3H6a3 3 0 00-3 3v8a3 3 0 003 3z"/>
              </svg>
            </div>
            <h3 class="font-semibold text-gray-900 text-sm">{{ lender.name }}</h3>
            <p class="text-xs text-gray-400 mt-1">{{ lender.note }}</p>
          </div>
        </div>
      </div>
    </section>

    <!-- ── Loan terms explained ──────────────────────────────────────── -->
    <section class="py-16 bg-gray-50">
      <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
        <h2 class="text-2xl font-bold text-gray-900 mb-10 text-center" data-aos="fade-up">
          Understanding Your Loan
        </h2>
        <div class="space-y-4">
          <details v-for="term in loanTerms" :key="term.term"
                   class="bg-white rounded-2xl border border-gray-100 shadow-sm overflow-hidden"
                   data-aos="fade-up">
            <summary class="cursor-pointer px-6 py-4 font-semibold text-gray-900 flex items-center justify-between select-none hover:bg-gray-50 transition-colors">
              {{ term.term }}
              <svg class="w-4 h-4 text-gray-400 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"/>
              </svg>
            </summary>
            <div class="px-6 pb-5 text-gray-600 text-sm leading-relaxed border-t border-gray-100 pt-4">
              {{ term.explanation }}
            </div>
          </details>
        </div>
      </div>
    </section>

    <!-- ── Application links / CTA ───────────────────────────────────── -->
    <section class="py-16 bg-dark-800">
      <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 text-center" data-aos="fade-up">
        <h2 class="text-3xl font-extrabold text-white mb-4">
          <!-- [CTA_HEADLINE] -->
          Ready to Get Behind the Wheel?
        </h2>
        <p class="text-gray-400 mb-10 max-w-xl mx-auto">
          <!-- [CTA_SUBTEXT] -->
          Call us or stop by. Our finance team will walk you through every option and
          get you approved quickly — no pressure, no hidden fees.
        </p>

        <!-- [APPLICATION_LINKS] Add or replace these with actual application form URLs -->
        <div class="grid sm:grid-cols-3 gap-4 mb-10">
          <div v-for="action in ctaActions" :key="action.label"
               class="bg-dark-700 rounded-2xl p-6 border border-white/10">
            <p class="text-white font-semibold mb-1">{{ action.label }}</p>
            <p class="text-gray-400 text-sm">{{ action.detail }}</p>
          </div>
        </div>

        <div class="flex flex-wrap justify-center gap-4">
          <RouterLink to="/contact" class="btn-primary text-base px-10 py-4">
            Apply Online
          </RouterLink>
          <a :href="'tel:' + PHONE" class="btn-secondary text-base px-10 py-4">
            Call {{ PHONE }}
          </a>
        </div>
      </div>
    </section>

  </PageLayout>
</template>

<script setup>
import { ref, computed } from 'vue'
import { RouterLink } from 'vue-router'
import PageLayout from '../components/layout/PageLayout.vue'
import { PHONE } from '../config'

// ── Rate calculator state ────────────────────────────────────────────────
const calc = ref({ price: 25000, down: 3000, rate: 6.9, term: 60 })

const loanAmount = computed(() => Math.max(0, (calc.value.price || 0) - (calc.value.down || 0)))

const monthlyPayment = computed(() => {
  const P = loanAmount.value
  const r = (calc.value.rate || 0) / 100 / 12
  const n = calc.value.term || 60
  if (P <= 0) return 0
  if (r === 0) return P / n
  return (P * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1)
})

const totalPaid = computed(() =>
  monthlyPayment.value > 0 ? monthlyPayment.value * calc.value.term : 0
)

// ── Static content (customize per client) ───────────────────────────────

// [BENEFITS] — customize titles and descriptions for each client
const benefits = [
  {
    title: 'All Credit Situations',
    description: 'First-time buyer, rebuilding credit, or excellent score — we have lenders for every situation.',
    icon: CheckIcon,
  },
  {
    title: 'Quick Pre-Approval',
    description: 'Get a decision in minutes. No long waits or confusing paperwork.',
    icon: ClockIcon,
  },
  {
    title: 'Competitive Rates',
    description: 'We shop multiple lenders on your behalf to find the lowest rate available.',
    icon: DollarIcon,
  },
]

// [LENDERS] — replace with real lender names or remove cards you don't use
const lenders = [
  { name: '[Lender Name 1]',  note: 'National bank partner' },
  { name: '[Lender Name 2]',  note: 'Credit union partner' },
  { name: '[Lender Name 3]',  note: 'Finance company' },
  { name: '[Lender Name 4]',  note: 'In-house financing' },
]

// [LOAN_TERMS] — educational content; customize or add more as needed
const loanTerms = [
  {
    term: 'APR (Annual Percentage Rate)',
    explanation: 'APR is the yearly cost of borrowing, including interest and fees, expressed as a percentage. A lower APR means you pay less over the life of the loan.',
  },
  {
    term: 'Down Payment',
    explanation: 'The amount you pay upfront. A larger down payment reduces your loan amount, lowers your monthly payment, and can help you qualify for better rates.',
  },
  {
    term: 'Loan Term',
    explanation: 'The length of your loan in months. Shorter terms mean higher monthly payments but less total interest. Longer terms lower your payment but increase total cost.',
  },
  {
    term: 'Credit Score Impact',
    explanation: 'Your credit score is one of the biggest factors in your rate. Scores above 700 generally qualify for the best rates. We also work with buyers rebuilding credit.',
  },
  {
    term: 'Pre-Approval vs. Pre-Qualification',
    explanation: 'Pre-qualification is a quick estimate based on self-reported info. Pre-approval involves a credit check and gives you a firmer commitment from a lender.',
  },
]

// [CTA_ACTIONS] — update with your dealership's specifics
const ctaActions = [
  { label: 'Call or Text', detail: PHONE },
  { label: 'Visit Us', detail: 'No appointment needed' },
  { label: 'Apply Online', detail: 'Takes about 5 minutes' },
]

// ── Inline icon components (avoids external icon dependency) ────────────
const CheckIcon = {
  template: `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/></svg>`,
}
const ClockIcon = {
  template: `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"/></svg>`,
}
const DollarIcon = {
  template: `<svg fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/></svg>`,
}
</script>
