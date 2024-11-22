export const DoctorIllustration: React.FC = () => {
  return (
    <div className="relative w-full max-w-2xl">
      <div className="absolute top-0 right-0 w-48 h-48 bg-indigo-100 rounded-full filter blur-3xl opacity-70" />
      <div className="absolute bottom-0 left-0 w-48 h-48 bg-indigo-100 rounded-full filter blur-3xl opacity-70" />
      <svg
        className="w-full h-auto"
        viewBox="0 0 898 705"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <g>
          <path d="M0 0h898v705H0z" fill="#6C63FF" fillOpacity=".1" />
          <path
            d="M300 200c0-66.274 53.726-120 120-120s120 53.726 120 120v160h-240V200z"
            fill="#E8F5FF"
          />
          <path
            d="M360 140c0-33.137 26.863-60 60-60s60 26.863 60 60v80h-120v-80z"
            fill="#FFC1C7"
          />
        </g>
      </svg>
    </div>
  );
};
