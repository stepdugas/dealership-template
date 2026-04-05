<template>
  <div class="min-h-screen bg-slate-950 flex flex-col">

    <!-- Top bar -->
    <header class="bg-slate-900 border-b border-slate-800 px-6 py-4 flex items-center justify-between flex-shrink-0">
      <div class="flex items-center gap-3">
        <div class="w-8 h-8 rounded-lg bg-blue-600 flex items-center justify-center">
          <svg class="w-4 h-4 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 17a2 2 0 11-4 0 2 2 0 014 0zM19 17a2 2 0 11-4 0 2 2 0 014 0zM13 16V6a1 1 0 00-1-1H4a1 1 0 00-1 1v10l2 1h8zM13 16l2 1h4l2-1v-5l-3.5-5.5H13v5z"/>
          </svg>
        </div>
        <span class="text-white font-semibold text-sm">Dealership Setup</span>
      </div>
      <!-- Mobile step indicator -->
      <span class="text-slate-400 text-xs md:hidden">Step {{ currentStep + 1 }} of {{ steps.length }}</span>
      <span class="text-slate-400 text-xs hidden md:block">Your info is saved as you go</span>
    </header>

    <div class="flex flex-1 overflow-hidden">

      <!-- ── Sidebar ── -->
      <aside class="hidden md:flex w-72 bg-slate-900 border-r border-slate-800 flex-col flex-shrink-0">
        <div class="p-6 flex-1 overflow-y-auto">
          <p class="text-slate-500 text-xs font-semibold uppercase tracking-widest mb-4">Setup Checklist</p>
          <nav class="space-y-1">
            <button
              v-for="(step, i) in steps"
              :key="i"
              @click="goToStep(i)"
              :disabled="i > furthestStep"
              class="w-full flex items-center gap-3 px-3 py-2.5 rounded-lg text-left transition-all"
              :class="[
                i === currentStep
                  ? 'bg-blue-600/20 border border-blue-500/40'
                  : i < currentStep
                    ? 'hover:bg-slate-800 cursor-pointer'
                    : 'opacity-40 cursor-not-allowed',
              ]"
            >
              <!-- Step indicator -->
              <div
                class="w-6 h-6 rounded-full flex items-center justify-center flex-shrink-0 text-xs font-bold"
                :class="i < currentStep
                  ? 'bg-green-500 text-white'
                  : i === currentStep
                    ? 'bg-blue-500 text-white'
                    : 'bg-slate-700 text-slate-400'"
              >
                <svg v-if="i < currentStep" class="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="3" d="M5 13l4 4L19 7"/>
                </svg>
                <span v-else>{{ i + 1 }}</span>
              </div>
              <div class="min-w-0">
                <p class="text-sm font-medium truncate" :class="i === currentStep ? 'text-white' : i < currentStep ? 'text-slate-300' : 'text-slate-500'">
                  {{ step.label }}
                </p>
                <p v-if="step.sublabel" class="text-xs truncate" :class="i === currentStep ? 'text-blue-300' : 'text-slate-600'">
                  {{ step.sublabel }}
                </p>
              </div>
            </button>
          </nav>
        </div>

        <!-- Sidebar footer -->
        <div class="p-6 border-t border-slate-800">
          <p class="text-xs text-slate-500 leading-relaxed">
            Questions? Email us at<br />
            <a href="mailto:dugas.openclaw@gmail.com" class="text-blue-400 hover:underline">dugas.openclaw@gmail.com</a>
          </p>
        </div>
      </aside>

      <!-- ── Main content ── -->
      <main class="flex-1 overflow-y-auto">

        <!-- Success screen -->
        <div v-if="submitted" class="flex items-center justify-center min-h-full p-8">
          <div class="text-center max-w-md">
            <div class="w-20 h-20 bg-green-500/20 rounded-full flex items-center justify-center mx-auto mb-6">
              <svg class="w-10 h-10 text-green-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"/>
              </svg>
            </div>
            <h2 class="text-2xl font-bold text-white mb-3">You're all set!</h2>
            <p class="text-slate-400 leading-relaxed">
              We've received everything we need to get your site built.
              We'll be in touch within 1–2 business days with next steps.
            </p>
            <p class="text-slate-500 text-sm mt-4">Check your email for a confirmation.</p>
          </div>
        </div>

        <!-- Step content -->
        <div v-else class="max-w-2xl mx-auto p-6 md:p-10">

          <!-- Mobile step pills -->
          <div class="flex gap-1.5 mb-8 md:hidden overflow-x-auto pb-1">
            <div
              v-for="(step, i) in steps"
              :key="i"
              class="h-1.5 rounded-full flex-shrink-0 transition-all"
              :class="[
                i < currentStep ? 'bg-green-500' : i === currentStep ? 'bg-blue-500' : 'bg-slate-700',
                i === currentStep ? 'w-8' : 'w-4',
              ]"
            ></div>
          </div>

          <Transition :name="direction === 'forward' ? 'slide-left' : 'slide-right'" mode="out-in">
            <div :key="currentStep">

              <!-- ────────────────────────────────────────────── -->
              <!-- STEP 0: Welcome                               -->
              <!-- ────────────────────────────────────────────── -->
              <div v-if="currentStep === 0">
                <div class="mb-2 text-blue-400 text-sm font-semibold uppercase tracking-widest">Welcome</div>
                <h1 class="text-3xl font-extrabold text-white mb-4">Let's get your dealership online.</h1>
                <p class="text-slate-400 text-lg leading-relaxed mb-8">
                  This short setup walks you through everything we need to build and launch your website.
                  It takes about <span class="text-white font-medium">5–10 minutes</span> and you can always
                  update anything later from your admin dashboard.
                </p>
                <div class="grid grid-cols-1 sm:grid-cols-2 gap-4 mb-10">
                  <div v-for="item in welcomeItems" :key="item.label" class="bg-slate-800/60 border border-slate-700 rounded-xl p-4 flex items-start gap-3">
                    <div class="w-8 h-8 rounded-lg bg-blue-600/20 flex items-center justify-center flex-shrink-0 mt-0.5">
                      <svg class="w-4 h-4 text-blue-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" :d="item.icon"/>
                      </svg>
                    </div>
                    <div>
                      <p class="text-sm font-semibold text-white">{{ item.label }}</p>
                      <p class="text-xs text-slate-400 mt-0.5">{{ item.desc }}</p>
                    </div>
                  </div>
                </div>
              </div>

              <!-- ────────────────────────────────────────────── -->
              <!-- STEP 1: Business Info                         -->
              <!-- ────────────────────────────────────────────── -->
              <div v-else-if="currentStep === 1">
                <StepHeader
                  eyebrow="Step 1 of 9"
                  title="Business Information"
                  desc="This is the core info that appears throughout your site — your name, how customers reach you, and where you're located."
                />
                <div class="space-y-5">
                  <div class="grid sm:grid-cols-2 gap-5">
                    <div class="sm:col-span-2">
                      <label class="field-label">Dealership Name *</label>
                      <input v-model="form.businessName" type="text" class="field-input" :class="{'border-red-500': errors.businessName}" placeholder="e.g. Smith Auto Group" />
                      <p v-if="errors.businessName" class="field-error">{{ errors.businessName }}</p>
                    </div>
                    <div class="sm:col-span-2">
                      <label class="field-label">Tagline <span class="text-slate-500 font-normal">(optional)</span></label>
                      <input v-model="form.tagline" type="text" class="field-input" placeholder="e.g. Quality Cars, Honest Deals" />
                    </div>
                    <div>
                      <label class="field-label">Phone Number *</label>
                      <input v-model="form.phone" type="tel" class="field-input" :class="{'border-red-500': errors.phone}" placeholder="(555) 123-4567" />
                      <p v-if="errors.phone" class="field-error">{{ errors.phone }}</p>
                    </div>
                    <div>
                      <label class="field-label">Email Address *</label>
                      <input v-model="form.email" type="email" class="field-input" :class="{'border-red-500': errors.email}" placeholder="contact@yourdealership.com" />
                      <p v-if="errors.email" class="field-error">{{ errors.email }}</p>
                    </div>
                    <div class="sm:col-span-2">
                      <label class="field-label">Street Address *</label>
                      <input v-model="form.address" type="text" class="field-input" :class="{'border-red-500': errors.address}" placeholder="123 Main Street" />
                      <p v-if="errors.address" class="field-error">{{ errors.address }}</p>
                    </div>
                    <div>
                      <label class="field-label">City *</label>
                      <input v-model="form.city" type="text" class="field-input" :class="{'border-red-500': errors.city}" placeholder="Akron" />
                      <p v-if="errors.city" class="field-error">{{ errors.city }}</p>
                    </div>
                    <div class="grid grid-cols-2 gap-3">
                      <div>
                        <label class="field-label">State *</label>
                        <input v-model="form.state" type="text" class="field-input" :class="{'border-red-500': errors.state}" placeholder="OH" maxlength="2" />
                        <p v-if="errors.state" class="field-error">{{ errors.state }}</p>
                      </div>
                      <div>
                        <label class="field-label">ZIP *</label>
                        <input v-model="form.zip" type="text" class="field-input" :class="{'border-red-500': errors.zip}" placeholder="44301" />
                        <p v-if="errors.zip" class="field-error">{{ errors.zip }}</p>
                      </div>
                    </div>
                    <div>
                      <label class="field-label">Facebook <span class="text-slate-500 font-normal">(optional)</span></label>
                      <input v-model="form.facebook" type="url" class="field-input" placeholder="https://facebook.com/yourpage" />
                    </div>
                    <div>
                      <label class="field-label">Instagram <span class="text-slate-500 font-normal">(optional)</span></label>
                      <input v-model="form.instagram" type="url" class="field-input" placeholder="https://instagram.com/yourpage" />
                    </div>
                  </div>
                </div>
              </div>

              <!-- ────────────────────────────────────────────── -->
              <!-- STEP 2: Branding                              -->
              <!-- ────────────────────────────────────────────── -->
              <div v-else-if="currentStep === 2">
                <StepHeader
                  eyebrow="Step 2 of 9"
                  title="Branding"
                  desc="Upload your logo and pick a color. This is what gives your site its look and feel."
                />
                <div class="space-y-6">
                  <div>
                    <label class="field-label">Logo <span class="text-slate-500 font-normal">(optional)</span></label>
                    <p class="text-xs text-slate-400 mb-3">Appears in the header and footer. PNG with transparent background works best.</p>
                    <div class="flex items-start gap-4">
                      <div class="w-24 h-24 rounded-xl bg-slate-800 border border-slate-700 flex items-center justify-center flex-shrink-0 overflow-hidden">
                        <img v-if="form.logoUrl" :src="form.logoUrl" alt="Logo preview" class="w-full h-full object-contain p-2" />
                        <svg v-else class="w-8 h-8 text-slate-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"/>
                        </svg>
                      </div>
                      <div class="flex-1 space-y-2">
                        <input v-model="form.logoUrl" type="url" class="field-input" placeholder="Paste an image URL, or upload below" />
                        <label class="flex items-center gap-2 px-4 py-2 bg-slate-800 border border-slate-700 rounded-lg text-sm text-slate-300 hover:bg-slate-700 cursor-pointer transition w-fit">
                          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-8l-4-4m0 0L8 8m4-4v12"/>
                          </svg>
                          {{ logoUploading ? 'Uploading…' : 'Upload from computer' }}
                          <input type="file" accept="image/*" class="hidden" @change="uploadLogo" :disabled="logoUploading" />
                        </label>
                        <p class="text-xs text-slate-500">No logo? No problem — your business name will show instead.</p>
                      </div>
                    </div>
                  </div>

                  <div>
                    <label class="field-label">Primary Brand Color</label>
                    <p class="text-xs text-slate-400 mb-3">Used for buttons, accents, and highlights across your site.</p>
                    <div class="flex items-center gap-4">
                      <input v-model="form.primaryColor" type="color" class="w-14 h-14 rounded-xl cursor-pointer border-2 border-slate-700 bg-transparent" />
                      <div>
                        <p class="text-white font-mono text-sm">{{ form.primaryColor }}</p>
                        <p class="text-slate-500 text-xs mt-0.5">Click the swatch to change</p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- ────────────────────────────────────────────── -->
              <!-- STEP 3: Business Hours                        -->
              <!-- ────────────────────────────────────────────── -->
              <div v-else-if="currentStep === 3">
                <StepHeader
                  eyebrow="Step 3 of 9"
                  title="Business Hours"
                  desc="Shown in your footer, contact page, and homepage. Check 'Closed' for any day you're not open."
                />
                <div class="space-y-2">
                  <div
                    v-for="day in days"
                    :key="day.key"
                    class="flex items-center gap-3 p-3 rounded-xl border transition-colors"
                    :class="form.hours[day.key].closed ? 'bg-slate-800/30 border-slate-800' : 'bg-slate-800/60 border-slate-700'"
                  >
                    <span class="w-24 text-sm font-medium flex-shrink-0" :class="form.hours[day.key].closed ? 'text-slate-500' : 'text-slate-200'">{{ day.label }}</span>
                    <template v-if="!form.hours[day.key].closed">
                      <input v-model="form.hours[day.key].open" type="time" class="field-input-sm" />
                      <span class="text-slate-500 text-sm">to</span>
                      <input v-model="form.hours[day.key].close" type="time" class="field-input-sm" />
                    </template>
                    <span v-else class="text-slate-500 text-sm italic flex-1">Closed</span>
                    <label class="flex items-center gap-2 ml-auto cursor-pointer flex-shrink-0">
                      <input v-model="form.hours[day.key].closed" type="checkbox" class="w-4 h-4 accent-blue-500 cursor-pointer" />
                      <span class="text-xs text-slate-400 select-none">Closed</span>
                    </label>
                  </div>
                </div>
              </div>

              <!-- ────────────────────────────────────────────── -->
              <!-- STEP 4: About Us                             -->
              <!-- ────────────────────────────────────────────── -->
              <div v-else-if="currentStep === 4">
                <StepHeader
                  eyebrow="Step 4 of 9"
                  title="About Us"
                  desc="This content appears on your About Us page. Tell customers who you are and why they should choose you."
                />
                <div class="space-y-5">
                  <div>
                    <label class="field-label">Your Story *</label>
                    <p class="text-xs text-slate-400 mb-2">Write it like you're talking to a customer. 2–4 sentences works great.</p>
                    <textarea
                      v-model="form.aboutBlurb"
                      class="field-input resize-none"
                      :class="{'border-red-500': errors.aboutBlurb}"
                      rows="4"
                      placeholder="e.g. Family-owned since 1998, we've been serving the Akron area with quality vehicles and honest deals. No pressure, no gimmicks — just a team that actually cares about getting you into the right car at the right price."
                    ></textarea>
                    <p v-if="errors.aboutBlurb" class="field-error">{{ errors.aboutBlurb }}</p>
                  </div>
                  <div>
                    <label class="field-label">Mission Statement <span class="text-slate-500 font-normal">(optional)</span></label>
                    <p class="text-xs text-slate-400 mb-2">A short quote that sums up what your dealership stands for.</p>
                    <textarea
                      v-model="form.aboutMission"
                      class="field-input resize-none"
                      rows="2"
                      placeholder='e.g. "To provide every customer with an honest, transparent, and enjoyable car-buying experience."'
                    ></textarea>
                  </div>
                  <div>
                    <label class="field-label">Year Founded <span class="text-slate-500 font-normal">(optional)</span></label>
                    <input v-model="form.aboutYearFounded" type="text" class="field-input max-w-xs" placeholder="e.g. 1998" />
                  </div>
                  <div>
                    <label class="field-label">Bragging Rights <span class="text-slate-500 font-normal">(optional — leave blank to hide)</span></label>
                    <p class="text-xs text-slate-400 mb-3">These show as big bold stats on your About page. Only fill in what you're proud of.</p>
                    <div class="grid grid-cols-2 gap-3">
                      <div>
                        <label class="text-xs text-slate-400 mb-1 block">Years in Business</label>
                        <input v-model="form.aboutStatYears" type="text" class="field-input" placeholder="e.g. 25+" />
                      </div>
                      <div>
                        <label class="text-xs text-slate-400 mb-1 block">Vehicles Sold</label>
                        <input v-model="form.aboutStatVehicles" type="text" class="field-input" placeholder="e.g. 2,000+" />
                      </div>
                      <div>
                        <label class="text-xs text-slate-400 mb-1 block">5-Star Reviews</label>
                        <input v-model="form.aboutStatReviews" type="text" class="field-input" placeholder="e.g. 500+" />
                      </div>
                      <div>
                        <label class="text-xs text-slate-400 mb-1 block">Team Members</label>
                        <input v-model="form.aboutStatTeam" type="text" class="field-input" placeholder="e.g. 12" />
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- ────────────────────────────────────────────── -->
              <!-- STEP 5: Meet the Staff                        -->
              <!-- ────────────────────────────────────────────── -->
              <div v-else-if="currentStep === 5">
                <StepHeader
                  eyebrow="Step 5 of 9"
                  title="Meet the Staff"
                  desc="A dedicated page to introduce your team — photos, names, titles, and bios."
                />
                <PageToggle
                  v-model="form.pages.staff"
                  label="Include a Meet the Staff page on my site"
                />
                <Transition name="fade">
                  <div v-if="form.pages.staff" class="mt-6 p-5 bg-slate-800/50 border border-slate-700 rounded-xl">
                    <div class="flex items-start gap-3">
                      <div class="w-8 h-8 rounded-lg bg-blue-600/20 flex items-center justify-center flex-shrink-0 mt-0.5">
                        <svg class="w-4 h-4 text-blue-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
                        </svg>
                      </div>
                      <div>
                        <p class="text-sm font-medium text-white mb-1">You'll add staff from your admin dashboard</p>
                        <p class="text-sm text-slate-400 leading-relaxed">After your site is live, log in to your admin panel and go to the <strong class="text-slate-200">Staff</strong> section. You can add photos, names, titles, and bios for each team member. Only fill in the fields that apply — blank fields are automatically hidden.</p>
                      </div>
                    </div>
                  </div>
                </Transition>
              </div>

              <!-- ────────────────────────────────────────────── -->
              <!-- STEP 6: Financing                             -->
              <!-- ────────────────────────────────────────────── -->
              <div v-else-if="currentStep === 6">
                <StepHeader
                  eyebrow="Step 6 of 9"
                  title="Financing"
                  desc="A financing page with a payment calculator and information about your financing options."
                />
                <PageToggle
                  v-model="form.pages.financing"
                  label="Include a Financing page on my site"
                />
                <Transition name="fade">
                  <div v-if="form.pages.financing" class="mt-6 space-y-5">
                    <div>
                      <label class="field-label">Your Financing Pitch <span class="text-slate-500 font-normal">(optional)</span></label>
                      <p class="text-xs text-slate-400 mb-2">Tell customers what makes your financing great. Works with any lenders you use.</p>
                      <textarea
                        v-model="form.financingBlurb"
                        class="field-input resize-none"
                        rows="3"
                        placeholder="e.g. We work with over 10 lenders to get you approved — good credit, bad credit, or first-time buyer. Come in and we'll find a payment that works for you."
                      ></textarea>
                    </div>
                    <div>
                      <label class="field-label">Online Application Link <span class="text-slate-500 font-normal">(optional)</span></label>
                      <p class="text-xs text-slate-400 mb-2">If your lender gave you an online application link, paste it here. Otherwise customers fill out a short built-in form that gets emailed to you.</p>
                      <input v-model="form.financingApplyUrl" type="url" class="field-input" placeholder="https://your-lender.com/apply" />
                    </div>
                  </div>
                </Transition>
              </div>

              <!-- ────────────────────────────────────────────── -->
              <!-- STEP 7: Schedule Service                      -->
              <!-- ────────────────────────────────────────────── -->
              <div v-else-if="currentStep === 7">
                <StepHeader
                  eyebrow="Step 7 of 9"
                  title="Schedule Service"
                  desc="Let customers request service appointments online — either through your existing booking system or a simple built-in form."
                />
                <PageToggle
                  v-model="form.pages.scheduleService"
                  label="Include a Schedule Service page on my site"
                />
                <Transition name="fade">
                  <div v-if="form.pages.scheduleService" class="mt-6 space-y-5">
                    <div>
                      <label class="field-label">Intro Text <span class="text-slate-500 font-normal">(optional)</span></label>
                      <textarea
                        v-model="form.scheduleBlurb"
                        class="field-input resize-none"
                        rows="2"
                        placeholder="e.g. Our service team is ready to help. Book an appointment below or give us a call."
                      ></textarea>
                    </div>
                    <div>
                      <label class="field-label">Calendar Booking Link <span class="text-slate-500 font-normal">(optional)</span></label>
                      <p class="text-xs text-slate-400 mb-2">Works with Calendly, Cal.com, Google Calendar booking, Acuity, or any bookable link. Leave blank to use a built-in request form instead.</p>
                      <input v-model="form.scheduleCalendarUrl" type="url" class="field-input" placeholder="https://calendly.com/your-dealership" />
                    </div>
                  </div>
                </Transition>
              </div>

              <!-- ────────────────────────────────────────────── -->
              <!-- STEP 8: Domain & Notes                        -->
              <!-- ────────────────────────────────────────────── -->
              <div v-else-if="currentStep === 8">
                <StepHeader
                  eyebrow="Step 8 of 9"
                  title="Domain & Final Notes"
                  desc="Almost done! Just a couple last things."
                />
                <div class="space-y-5">
                  <div>
                    <label class="field-label">Desired Website Domain</label>
                    <p class="text-xs text-slate-400 mb-2">We'll check availability and help you get it set up.</p>
                    <input
                      v-model="form.domain"
                      type="text"
                      class="field-input"
                      placeholder="e.g. smithautosales.com — or 'I need help choosing one'"
                    />
                  </div>
                  <div>
                    <label class="field-label">Anything else we should know? <span class="text-slate-500 font-normal">(optional)</span></label>
                    <textarea
                      v-model="form.notes"
                      class="field-input resize-none"
                      rows="4"
                      placeholder="Special requests, specific features you want, things to avoid, preferred timeline, etc."
                    ></textarea>
                  </div>
                </div>
              </div>

              <!-- ────────────────────────────────────────────── -->
              <!-- STEP 9: Review & Submit                       -->
              <!-- ────────────────────────────────────────────── -->
              <div v-else-if="currentStep === 9">
                <StepHeader
                  eyebrow="Step 9 of 9"
                  title="Review & Submit"
                  desc="Take a look at what you've filled in. Click any section to go back and edit."
                />

                <div class="space-y-4">
                  <!-- Business Info -->
                  <ReviewCard title="Business Info" @edit="goToStep(1)">
                    <ReviewRow label="Name" :value="form.businessName" />
                    <ReviewRow label="Tagline" :value="form.tagline" />
                    <ReviewRow label="Phone" :value="form.phone" />
                    <ReviewRow label="Email" :value="form.email" />
                    <ReviewRow label="Address" :value="[form.address, form.city, form.state, form.zip].filter(Boolean).join(', ')" />
                  </ReviewCard>

                  <!-- Branding -->
                  <ReviewCard title="Branding" @edit="goToStep(2)">
                    <ReviewRow label="Logo" :value="form.logoUrl || 'Business name will show as text'" />
                    <ReviewRow label="Brand Color" :value="form.primaryColor">
                      <div class="w-5 h-5 rounded-full border border-slate-600 flex-shrink-0" :style="{ background: form.primaryColor }"></div>
                    </ReviewRow>
                  </ReviewCard>

                  <!-- Pages -->
                  <ReviewCard title="Optional Pages" @edit="goToStep(5)">
                    <ReviewRow label="Meet the Staff" :value="form.pages.staff ? 'Enabled' : 'Not included'" :highlight="form.pages.staff" />
                    <ReviewRow label="Financing" :value="form.pages.financing ? 'Enabled' : 'Not included'" :highlight="form.pages.financing" />
                    <ReviewRow label="Schedule Service" :value="form.pages.scheduleService ? 'Enabled' : 'Not included'" :highlight="form.pages.scheduleService" />
                  </ReviewCard>

                  <!-- Domain -->
                  <ReviewCard title="Domain & Notes" @edit="goToStep(8)">
                    <ReviewRow label="Domain" :value="form.domain || 'Not specified'" />
                    <ReviewRow label="Notes" :value="form.notes || 'None'" />
                  </ReviewCard>
                </div>

                <p v-if="submitError" class="mt-5 text-red-400 text-sm text-center">{{ submitError }}</p>

                <button
                  @click="submitForm"
                  :disabled="submitting"
                  class="mt-6 w-full py-4 bg-blue-600 hover:bg-blue-500 disabled:opacity-50 disabled:cursor-not-allowed text-white font-bold rounded-xl transition text-base"
                >
                  {{ submitting ? 'Submitting…' : 'Submit & Get Started 🚀' }}
                </button>
                <p class="text-xs text-slate-500 text-center mt-3">We'll review your info and reach out within 1–2 business days.</p>
              </div>

            </div>
          </Transition>

          <!-- Navigation buttons -->
          <div v-if="!submitted" class="flex items-center justify-between mt-10 pt-6 border-t border-slate-800">
            <button
              v-if="currentStep > 0"
              @click="prev"
              class="flex items-center gap-2 px-5 py-2.5 text-sm font-medium text-slate-400 hover:text-white bg-slate-800 hover:bg-slate-700 rounded-xl transition"
            >
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"/>
              </svg>
              Back
            </button>
            <div v-else></div>

            <button
              v-if="currentStep < steps.length - 1"
              @click="next"
              class="flex items-center gap-2 px-6 py-2.5 text-sm font-bold text-white bg-blue-600 hover:bg-blue-500 rounded-xl transition ml-auto"
            >
              {{ currentStep === 0 ? "Let's Get Started" : 'Continue' }}
              <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"/>
              </svg>
            </button>
          </div>

        </div>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed, defineComponent, h } from 'vue'
import axios from 'axios'
import { API_BASE_URL, CLOUDINARY_CLOUD_NAME, CLOUDINARY_UPLOAD_PRESET } from '../config'

// ── Steps definition ──────────────────────────────────────────────────
const steps = [
  { label: 'Welcome',           sublabel: 'Quick intro' },
  { label: 'Business Info',     sublabel: 'Name, phone, address' },
  { label: 'Branding',          sublabel: 'Logo & color' },
  { label: 'Business Hours',    sublabel: 'When you\'re open' },
  { label: 'About Us',          sublabel: 'Your story & mission' },
  { label: 'Meet the Staff',    sublabel: 'Optional page' },
  { label: 'Financing',         sublabel: 'Optional page' },
  { label: 'Schedule Service',  sublabel: 'Optional page' },
  { label: 'Domain & Notes',    sublabel: 'Final details' },
  { label: 'Review & Submit',   sublabel: 'Almost done!' },
]

const currentStep  = ref(0)
const furthestStep = ref(0)
const direction    = ref('forward')
const submitting   = ref(false)
const submitted    = ref(false)
const submitError  = ref('')
const logoUploading = ref(false)
const errors = ref({})

const days = [
  { key: 'Monday',    label: 'Monday' },
  { key: 'Tuesday',   label: 'Tuesday' },
  { key: 'Wednesday', label: 'Wednesday' },
  { key: 'Thursday',  label: 'Thursday' },
  { key: 'Friday',    label: 'Friday' },
  { key: 'Saturday',  label: 'Saturday' },
  { key: 'Sunday',    label: 'Sunday' },
]

// ── Form data ─────────────────────────────────────────────────────────
const form = reactive({
  businessName: '', tagline: '', phone: '', email: '',
  address: '', city: '', state: '', zip: '',
  facebook: '', instagram: '',
  logoUrl: '', primaryColor: '#6172f0',
  hours: {
    Monday:    { open: '09:00', close: '18:00', closed: false },
    Tuesday:   { open: '09:00', close: '18:00', closed: false },
    Wednesday: { open: '09:00', close: '18:00', closed: false },
    Thursday:  { open: '09:00', close: '18:00', closed: false },
    Friday:    { open: '09:00', close: '18:00', closed: false },
    Saturday:  { open: '10:00', close: '17:00', closed: false },
    Sunday:    { open: '',      close: '',      closed: true  },
  },
  aboutBlurb: '', aboutMission: '', aboutYearFounded: '',
  aboutStatYears: '', aboutStatVehicles: '', aboutStatReviews: '', aboutStatTeam: '',
  pages: { staff: false, financing: false, scheduleService: false },
  financingBlurb: '', financingApplyUrl: '',
  scheduleBlurb: '', scheduleCalendarUrl: '',
  domain: '', notes: '',
})

// ── Navigation ────────────────────────────────────────────────────────
function goToStep(i) {
  if (i > furthestStep.value) return
  direction.value = i > currentStep.value ? 'forward' : 'backward'
  currentStep.value = i
}

function next() {
  if (!validateStep()) return
  direction.value = 'forward'
  currentStep.value++
  if (currentStep.value > furthestStep.value) furthestStep.value = currentStep.value
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

function prev() {
  direction.value = 'backward'
  currentStep.value--
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

// ── Validation ────────────────────────────────────────────────────────
function validateStep() {
  errors.value = {}
  if (currentStep.value === 1) {
    if (!form.businessName.trim()) errors.value.businessName = 'Business name is required'
    if (!form.phone.trim())        errors.value.phone        = 'Phone number is required'
    if (!form.email.trim())        errors.value.email        = 'Email is required'
    if (!form.address.trim())      errors.value.address      = 'Address is required'
    if (!form.city.trim())         errors.value.city         = 'City is required'
    if (!form.state.trim())        errors.value.state        = 'State is required'
    if (!form.zip.trim())          errors.value.zip          = 'ZIP is required'
  }
  if (currentStep.value === 4) {
    if (!form.aboutBlurb.trim()) errors.value.aboutBlurb = 'Please write a short story about your dealership'
  }
  return Object.keys(errors.value).length === 0
}

// ── Logo upload ────────────────────────────────────────────────────────
async function uploadLogo(e) {
  const file = e.target.files?.[0]
  if (!file) return
  logoUploading.value = true
  try {
    const fd = new FormData()
    fd.append('file', file)
    fd.append('upload_preset', CLOUDINARY_UPLOAD_PRESET)
    const res  = await fetch(`https://api.cloudinary.com/v1_1/${CLOUDINARY_CLOUD_NAME}/image/upload`, { method: 'POST', body: fd })
    const data = await res.json()
    form.logoUrl = data.secure_url
  } catch { /* silently fail */ }
  finally { logoUploading.value = false }
}

// ── Submit ────────────────────────────────────────────────────────────
async function submitForm() {
  submitting.value = true
  submitError.value = ''
  try {
    // Flatten hours
    const hoursFlat = {}
    for (const [day, val] of Object.entries(form.hours)) {
      hoursFlat[day] = val.closed ? 'Closed' : `${val.open} – ${val.close}`
    }
    const pages = [
      form.pages.staff          && 'Meet the Staff',
      form.pages.financing      && 'Financing',
      form.pages.scheduleService && 'Schedule Service',
    ].filter(Boolean)

    await axios.post(`${API_BASE_URL}/api/client-intake`, {
      businessName:      form.businessName,
      tagline:           form.tagline,
      phone:             form.phone,
      email:             form.email,
      address:           form.address,
      city:              form.city,
      state:             form.state,
      zip:               form.zip,
      hours:             hoursFlat,
      primaryColor:      form.primaryColor,
      facebook:          form.facebook,
      instagram:         form.instagram,
      aboutBlurb:        form.aboutBlurb,
      aboutMission:      form.aboutMission,
      aboutYearFounded:  form.aboutYearFounded,
      aboutStatYears:    form.aboutStatYears,
      aboutStatVehicles: form.aboutStatVehicles,
      aboutStatReviews:  form.aboutStatReviews,
      aboutStatTeam:     form.aboutStatTeam,
      domain:            form.domain,
      pages,
      notes: [
        form.notes,
        form.financingBlurb     ? `Financing blurb: ${form.financingBlurb}`         : '',
        form.financingApplyUrl  ? `Financing URL: ${form.financingApplyUrl}`         : '',
        form.scheduleBlurb      ? `Schedule blurb: ${form.scheduleBlurb}`            : '',
        form.scheduleCalendarUrl ? `Calendar URL: ${form.scheduleCalendarUrl}`       : '',
        form.logoUrl            ? `Logo URL: ${form.logoUrl}`                        : '',
      ].filter(Boolean).join('\n'),
    })
    submitted.value = true
  } catch {
    submitError.value = 'Something went wrong. Please email us at dugas.openclaw@gmail.com'
  } finally {
    submitting.value = false
  }
}

// ── Welcome items ─────────────────────────────────────────────────────
const welcomeItems = [
  { label: 'Business Info', desc: 'Name, contact, and location', icon: 'M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-2 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4' },
  { label: 'Branding',      desc: 'Logo and brand color',        icon: 'M7 21a4 4 0 01-4-4V5a2 2 0 012-2h4a2 2 0 012 2v12a4 4 0 01-4 4zm0 0h12a2 2 0 002-2v-4a2 2 0 00-2-2h-2.343M11 7.343l1.657-1.657a2 2 0 012.828 0l2.829 2.829a2 2 0 010 2.828l-8.486 8.485M7 17h.01' },
  { label: 'Your Story',    desc: 'About you and your mission',  icon: 'M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253' },
  { label: 'Optional Pages', desc: 'Staff, Financing, Service',  icon: 'M4 6h16M4 10h16M4 14h16M4 18h16' },
]

// ── Sub-components ────────────────────────────────────────────────────
const StepHeader = defineComponent({
  props: ['eyebrow', 'title', 'desc'],
  setup(props) {
    return () => h('div', { class: 'mb-8' }, [
      h('p', { class: 'text-blue-400 text-xs font-semibold uppercase tracking-widest mb-2' }, props.eyebrow),
      h('h2', { class: 'text-2xl font-extrabold text-white mb-3' }, props.title),
      h('p', { class: 'text-slate-400 leading-relaxed' }, props.desc),
    ])
  },
})

const PageToggle = defineComponent({
  props: ['modelValue', 'label'],
  emits: ['update:modelValue'],
  setup(props, { emit }) {
    return () => h('div', { class: 'flex items-start gap-4 p-5 bg-slate-800/60 border border-slate-700 rounded-xl' }, [
      h('button', {
        type: 'button',
        onClick: () => emit('update:modelValue', !props.modelValue),
        class: `relative inline-flex h-6 w-11 flex-shrink-0 rounded-full border-2 border-transparent transition-colors duration-200 focus:outline-none cursor-pointer ${props.modelValue ? 'bg-blue-600' : 'bg-slate-600'}`,
      }, [
        h('span', {
          class: `inline-block h-5 w-5 transform rounded-full bg-white shadow transition duration-200 ${props.modelValue ? 'translate-x-5' : 'translate-x-0'}`,
        }),
      ]),
      h('div', {}, [
        h('p', { class: 'text-sm font-medium text-white' }, props.label),
        h('p', { class: 'text-xs text-slate-500 mt-0.5' }, 'You can change this anytime from your admin dashboard'),
      ]),
    ])
  },
})

const ReviewCard = defineComponent({
  props: ['title'],
  emits: ['edit'],
  setup(props, { slots, emit }) {
    return () => h('div', { class: 'bg-slate-800/50 border border-slate-700 rounded-xl overflow-hidden' }, [
      h('div', { class: 'flex items-center justify-between px-5 py-3 border-b border-slate-700 bg-slate-800/80' }, [
        h('p', { class: 'text-sm font-semibold text-white' }, props.title),
        h('button', { type: 'button', onClick: () => emit('edit'), class: 'text-xs text-blue-400 hover:text-blue-300 transition' }, 'Edit'),
      ]),
      h('div', { class: 'px-5 py-3 divide-y divide-slate-700/50' }, slots.default?.()),
    ])
  },
})

const ReviewRow = defineComponent({
  props: ['label', 'value', 'highlight'],
  setup(props, { slots }) {
    return () => h('div', { class: 'flex items-start gap-3 py-2.5' }, [
      h('span', { class: 'text-xs text-slate-500 w-28 flex-shrink-0 pt-0.5' }, props.label),
      slots.default?.(),
      h('span', {
        class: `text-sm flex-1 ${props.highlight ? 'text-green-400 font-medium' : 'text-slate-300'}`,
      }, props.value || '—'),
    ])
  },
})
</script>

<style scoped>
.field-label {
  @apply block text-sm font-medium text-slate-200 mb-1.5;
}
.field-input {
  @apply w-full px-3.5 py-2.5 bg-slate-800 border border-slate-600 rounded-xl text-sm text-white
         placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition;
}
.field-input-sm {
  @apply px-3 py-2 bg-slate-800 border border-slate-600 rounded-lg text-sm text-white
         focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition;
}
.field-error {
  @apply text-red-400 text-xs mt-1;
}

/* Step transitions */
.slide-left-enter-active,
.slide-left-leave-active,
.slide-right-enter-active,
.slide-right-leave-active {
  transition: all 0.22s ease;
}
.slide-left-enter-from  { opacity: 0; transform: translateX(28px); }
.slide-left-leave-to    { opacity: 0; transform: translateX(-28px); }
.slide-right-enter-from { opacity: 0; transform: translateX(-28px); }
.slide-right-leave-to   { opacity: 0; transform: translateX(28px); }

.fade-enter-active, .fade-leave-active { transition: opacity 0.2s; }
.fade-enter-from, .fade-leave-to { opacity: 0; }
</style>
