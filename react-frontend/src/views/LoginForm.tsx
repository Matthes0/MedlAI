import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import React, {useState} from "react";
import { useNavigate } from 'react-router-dom'

interface Credentials {
    username: string,
    password: string,
}
export const LoginForm = () => {
    const navigate = useNavigate()
    const queryClient = useQueryClient();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const mutation = useMutation({
        mutationFn: (credentials: Credentials) =>
            fetch("http://localhost:8080/api/admin/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json", // Set the correct Content-Type
                },
                body: JSON.stringify(credentials),
            }).then((res) => {
                if (!res.ok) {
                    throw new Error("Failed to send credentials");
                }
                return res.json();
            }),
        onSuccess: () => {
            alert("Zalogowano!");
            queryClient.invalidateQueries(["credentials"]);
            navigate("/admin")
        },
        onError: (error) => {
            alert(`Wystąpił błąd: ${error.message}`);
        },
    });
    const handlelogin = () => {
        if (username != "" && password != "") {
            console.log("credentials: ", username, " ", password);
            const credentials: Credentials = {username: username, password: password}
            mutation.mutate(credentials);
        } else {
            alert("Empty data forms");
        }
    };

    return (
        <form>
                <input type="text" name="username" onChange={(e) => setUsername(e.target.value)}/>
                <input type="password" name="password" onChange={(e) => setPassword(e.target.value)}/>
                <button type="submit" onSubmit={handlelogin}> Zaloguj się</button>
        </form>
);
};
