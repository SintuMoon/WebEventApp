/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,jsx}'],
  theme: {
    extend: {
      colors: {
        sand: {
          50: '#fdf8f2',
          100: '#f8ede0',
          200: '#f0dcc0',
          300: '#e5c8a3'
        },
        charcoal: '#2c2a29',
        blush: '#f9d9d0'
      }
    }
  },
  plugins: [require('@tailwindcss/forms')]
};
