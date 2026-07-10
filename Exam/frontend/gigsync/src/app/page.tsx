import styles from "./page.module.css";
import HomeButton from "@/components/HomeButton/HomeButton";

export default function Home() {

  return (
      <div className={styles.main}>
        <HomeButton  page={"/ticketing"}> Ticketing </HomeButton>
        <HomeButton  page={"/analyzer"}> Analyzer </HomeButton>
      </div>

  );
}
