/** @type {import('tailwindcss').Config} */
export default {
  content: [
    './index.html',
    './src/**/*.{vue,js,ts,jsx,tsx}',
  ],
  theme: {
    extend: {
      colors: {
        // Primary brand color — swap in config.js and here per client
        primary: {
          50:  '#f0f4ff',
          100: '#e0eaff',
          200: '#c7d7fd',
          300: '#a5bcfb',
          400: '#8098f7',
          500: '#6172f0',   // default primary
          600: '#4f55e5',
          700: '#4143cb',
          800: '#3638a5',
          900: '#303483',
          950: '#1e1f4d',
        },
        dark: {
          900: '#0a0a0f',
          800: '#111118',
          700: '#1a1a24',
          600: '#24242f',
        },
      },
      fontFamily: {
        sans: ['Inter', 'ui-sans-serif', 'system-ui', 'sans-serif'],
      },
    },
  },
  plugins: [],
}
