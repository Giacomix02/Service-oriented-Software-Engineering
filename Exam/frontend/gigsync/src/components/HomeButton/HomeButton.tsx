"use client"

import Link from 'next/link'
import s from "./HomeButton.module.css"


export default function HomeButton({ children, page }: { children: React.ReactNode, page: string }) {

    return(
        
            <Link href={page} className={s.homeButton}>
            {children}
            </Link>
    )
}