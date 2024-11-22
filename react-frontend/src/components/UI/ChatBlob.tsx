import { LoaderCircle } from "lucide-react";
import React from "react";
import { BeatLoader } from "react-spinners";

interface ChatBlobProps {
  name: string;
  role: string;
  message: string;
}

export const ChatBlob: React.FC<ChatBlobProps> = ({ message, name, role }) => {
  return (
    <div className="flex items-start gap-2.5 mt-5">
      {role === "AI" ? (
        <img
          className="w-8 h-8 rounded-full"
          src="https://cdn.britannica.com/43/199643-050-9086FECE/Josef-Mengele.jpg?w=400&h=300&c=crop"
          alt="Jese image"
        />
      ) : (
        <svg
          className="w-8 h-8 rounded-full"
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 112 112"
          fill="none"
          shape-rendering="auto"
          width="512"
          height="512"
        >
          <mask id="viewboxMask">
            <rect
              width="112"
              height="112"
              rx="0"
              ry="0"
              x="0"
              y="0"
              fill="#fff"
            />
          </mask>
          <g mask="url(#viewboxMask)">
            <rect fill="#edb98a" width="112" height="112" x="0" y="0" />
            <g transform="translate(2 63)">
              <rect
                x="22"
                y="7"
                width="64"
                height="26"
                rx="13"
                fill="#000"
                fill-opacity=".6"
              />
              <rect x="24" y="9" width="60" height="22" rx="11" fill="#fff" />
              <path
                d="M24.18 18H32V9.41A11 11 0 0 1 35 9h1v9h9V9h4v9h9V9h4v9h9V9h2c.68 0 1.35.06 2 .18V18h8.82l.05.28v3.44l-.05.28H75v8.82c-.65.12-1.32.18-2 .18h-2v-9h-9v9h-4v-9h-9v9h-4v-9h-9v9h-1a11 11 0 0 1-3-.41V22h-7.82a11.06 11.06 0 0 1 0-4Z"
                fill="#E6E6E6"
              />
            </g>
            <g transform="translate(28 51)">
              <path
                fill-rule="evenodd"
                clip-rule="evenodd"
                d="M16 8c0 4.42 5.37 8 12 8s12-3.58 12-8"
                fill="#000"
                fill-opacity=".16"
              />
            </g>
            <g transform="translate(0 19)">
              <path
                d="M44 20.73c0 4.26-6.27 7.72-14 7.72S16 25 16 20.73C16 16.46 22.27 13 30 13s14 3.46 14 7.73ZM96 20.73c0 4.26-6.27 7.72-14 7.72S68 25 68 20.73C68 16.46 74.27 13 82 13s14 3.46 14 7.73Z"
                fill="#fff"
              />
              <path
                d="M32.82 28.3a25.15 25.15 0 0 1-5.64 0 6 6 0 1 1 5.64 0ZM84.82 28.3a25.15 25.15 0 0 1-5.64 0 6 6 0 1 1 5.64 0Z"
                fill="#000"
                fill-opacity=".7"
              />
            </g>
            <g transform="translate(0 11)">
              <path
                d="M38.03 5.6c-1.48 8.38-14.1 14.17-23.24 10.42a2.04 2.04 0 0 0-2.64 1c-.43.97.04 2.1 1.05 2.5 11.45 4.7 26.84-2.37 28.76-13.3a1.92 1.92 0 0 0-1.64-2.2 2 2 0 0 0-2.3 1.57ZM73.97 5.6c1.48 8.38 14.1 14.17 23.24 10.42 1.02-.41 2.2.03 2.63 1 .43.97-.04 2.1-1.05 2.5-11.44 4.7-26.84-2.37-28.76-13.3a1.92 1.92 0 0 1 1.64-2.2 2 2 0 0 1 2.3 1.57Z"
                fill="#000"
                fill-opacity=".6"
              />
            </g>
          </g>
        </svg>
      )}

      <div
        className={`flex flex-col w-full max-w-[320px] leading-1.5 p-4 border-gray-200  rounded-e-xl rounded-es-xl ${
          role === "AI" ? "bg-blue-600" : "bg-gray-600"
        }`}
      >
        <div className="flex items-center space-x-2 rtl:space-x-reverse">
          <span className="text-sm font-semibold text-gray-900 dark:text-white">
            {!name ? "Dr. Mengele" : name}
          </span>
          <span className="text-sm font-normal text-gray-500 dark:text-gray-400">
            {!role ? "AI" : role}
          </span>
        </div>
        <p className="text-sm font-normal py-2.5 text-gray-900 dark:text-white">
          {!message ? <BeatLoader color="white" size={8} /> : message}
        </p>
      </div>
    </div>
  );
};
