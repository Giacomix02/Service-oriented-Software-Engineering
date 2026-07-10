import Image from "next/image";
import styles from "./page.module.css";
import HomeButton from "@/app/components/HomeButton/HomeButton";

export default function Home() {
  return (
      <div className={styles.main}>
        <HomeButton  page={"/pages/ticketing"}> Ticketing </HomeButton>
        <HomeButton  page={"/pages/analyzer"}> Analyzer </HomeButton>
      </div>
  );
}
