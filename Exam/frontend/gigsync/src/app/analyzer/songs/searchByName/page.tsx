import styles from "../songs.module.css";

export default function Page() {
    return (
        <div className={styles.pageContainer}>
            <h1 className={styles.pageTitle}>Search Songs by Name</h1>
            <div className={styles.cardsGrid}>

            </div>
        </div>
    )
}