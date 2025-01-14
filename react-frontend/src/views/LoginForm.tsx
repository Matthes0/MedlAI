import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import React, {useState} from "react";
import { useNavigate } from 'react-router-dom'

interface Credentials {
    user: string,
    password: string,
}
export const LoginForm = () => {
    const navigate = useNavigate()
    const queryClient = useQueryClient();
    const [user, setUser] = useState("");
    const [password, setPassword] = useState("");
    const mutation = useMutation({
        mutationFn: (credentials: Credentials) =>
            fetch("http://localhost:8080/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded", // Zmień na wymagany format
                },
                credentials: "include",
                body: new URLSearchParams({
                    username: credentials.user,
                    password: credentials.password,
                }).toString(),
            }).then(async (res) => {
                if (!res.ok) {
                    const errorData = await res.json();
                    throw new Error(errorData.error || "Failed to send credentials");
                }
                return res.json();
            }),
        onSuccess: () => {
            alert("Zalogowano!");
            queryClient.invalidateQueries(["credentials"]);
            navigate("/admin");
        },
        onError: (error) => {
            alert(`Wystąpił błąd: ${error.message}`);
        },
    });
    const handleLogin = (event: React.FormEvent) => {
        event.preventDefault(); // Prevent form from submitting
        if (user !== "" && password !== "") {
            console.log("credentials: ", user, " ", password);
            const credentials: Credentials = { user, password };
            mutation.mutate(credentials);
        } else {
            alert("Empty data forms");
        }
    };

    return (
        <form onSubmit={handleLogin}>
            <input
                type="text"
                name="username"
                placeholder="Username"
                value={user}
                onChange={(e) => setUser(e.target.value)}
            />
            <input
                type="password"
                name="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button type="submit">Zaloguj się</button>
        </form>
);
};
