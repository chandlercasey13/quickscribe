import { cn } from "@/lib/utils"
import { Button } from "@/components/ui/button"
import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import {
    Field,
    FieldDescription,
    FieldGroup,
    FieldLabel,
} from "@/components/ui/field"
import { Input } from "@/components/ui/input"
import { useState } from "react"

const API_URL = import.meta.env.VITE_API_URL ?? ""

export default function LoginForm({
                                      className,
                                      ...props
                                  }: React.ComponentProps<"div">) {
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState<string | null>(null)

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        setError(null)
        setLoading(true)

        try {
            const formData = new FormData(e.currentTarget)
            const email = String(formData.get("email") ?? "")
            const password = String(formData.get("password") ?? "")

            const res = await fetch(`${API_URL}/auth/login`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email, password }),
                // include this ONLY if you are using cookies/sessions:
                // credentials: "include",
            })

            if (!res.ok) {
                // try to read a helpful message from backend
                let msg = "Login failed"
                try {
                    const data = await res.json()
                    msg = data?.message ?? msg
                } catch {
                    // ignore if not JSON
                }
                throw new Error(msg)
            }

            // If backend returns token/json:
            // const data = await res.json()
            // console.log("login ok", data)

            console.log("login ok")
            e.currentTarget.reset()
        } catch (err) {
            setError(err instanceof Error ? err.message : "Login failed")
        } finally {
            setLoading(false)
        }
    }

    return (
        <div className={cn("flex flex-col gap-6", className)} {...props}>
            <Card>
                <CardHeader>
                    <CardTitle>Login to your account</CardTitle>
                    <CardDescription>
                        Enter your email below to login to your account
                    </CardDescription>
                </CardHeader>

                <CardContent>
                    <form onSubmit={handleSubmit}>
                        <FieldGroup>
                            <Field>
                                <FieldLabel htmlFor="email">Email</FieldLabel>
                                <Input
                                    id="email"
                                    name="email"
                                    type="email"
                                    placeholder="m@example.com"
                                    required
                                />
                            </Field>

                            <Field>
                                <div className="flex items-center">
                                    <FieldLabel htmlFor="password">Password</FieldLabel>
                                    <a
                                        href="#"
                                        className="ml-auto inline-block text-sm underline-offset-4 hover:underline"
                                    >
                                        Forgot your password?
                                    </a>
                                </div>
                                <Input id="password" name="password" type="password" required />
                            </Field>

                            <Field>
                                <Button type="submit" disabled={loading}>
                                    {loading ? "Logging in..." : "Login"}
                                </Button>

                                <Button variant="outline" type="button" disabled={loading}>
                                    Login with Google
                                </Button>

                                {error && (
                                    <FieldDescription className="text-center text-destructive">
                                        {error}
                                    </FieldDescription>
                                )}

                                <FieldDescription className="text-center">
                                    Don&apos;t have an account? <a href="#">Sign up</a>
                                </FieldDescription>
                            </Field>
                        </FieldGroup>
                    </form>
                </CardContent>
            </Card>
        </div>
    )
}
