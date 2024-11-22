/** @type {import('tailwindcss').Config} */
export default {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      backgroundImage: {
        "header-doc": "url(./assets/hero-doc.svg)",
        blob: "url(./assets/blob.svg)",
      },
    },
  },
  plugins: [],
};
