import Sidebar from "../components/Sidebar";
import TopBar from "../components/TopBar";
import "../styles/layout.css";
import PageTransition from "../components/PageTransition";

function MainLayout({ children }) {
  return (
    <>
	
      <Sidebar />
      <TopBar />

      <div className="app-content">
	
        <main className="main-content">
          {children}
        </main>
      </div>
    </>
  );
}

export default MainLayout;