import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import PrivateRoute from "./PrivateRoute";
import NovoProduto from "../pages/NovoProduto";
import Dashboard from "../pages/Dashboard";
import Produtos from "../pages/Produtos";
import Login from "../pages/Login";
import Categorias from "../pages/Categorias";
import EditarProduto from "../pages/EditarProduto";
import Movimentacoes from "../pages/Movimentacoes";
import NovaEntrada from "../pages/NovaEntrada";
import Fornecedores from "../pages/Fornecedores";
import NovoFornecedor from "../pages/NovoFornecedor";
import EditarFornecedor from "../pages/EditarFornecedor";
import Setores from "../pages/Setores";
import NovoSetor from "../pages/NovoSetor";
import EditarSetor from "../pages/EditarSetor";
import NovaSaida from "../pages/NovaSaida";
import Lotes from "../pages/Lotes";
import LotesProduto from "../pages/LotesProduto";
import Auditoria from "../pages/Auditoria";
import Prefeituras from "../pages/Prefeituras";
import PrefeituraForm from "../pages/PrefeituraForm";
import Usuarios from "../pages/Usuarios";
import UsuarioForm from "../pages/UsuarioForm";



function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
	<Route path="/"element={<Navigate to="/login" replace />}/>
	<Route path="/login" element={<Login />} /> {/* Rota adicionada */}
        <Route path="/" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
        <Route path="/produtos" element={<PrivateRoute><Produtos /></PrivateRoute>} />
	<Route  path="/produtos/novo"element= {<PrivateRoute><NovoProduto /></PrivateRoute>}/>
	<Route	 path="/categorias"element={<PrivateRoute><Categorias /></PrivateRoute>}/>
	<Route  path="/produtos/editar/:id"element={<PrivateRoute><EditarProduto /></PrivateRoute>}/>
	<Route path="/movimentacoes"element={<PrivateRoute><Movimentacoes /></PrivateRoute>}/>
	<Route path="/movimentacoes/entrada"element={<PrivateRoute><NovaEntrada /></PrivateRoute>}/>
	<Route path="/fornecedores"element={<PrivateRoute><Fornecedores /></PrivateRoute>}/>
	<Route path="/fornecedores/novo"element={<PrivateRoute><NovoFornecedor /></PrivateRoute>}/>
	<Route path="/fornecedores/editar/:id"element={<PrivateRoute><EditarFornecedor /></PrivateRoute>}/>
	<Route path="/setores" element={<PrivateRoute><Setores /></PrivateRoute>} />
	<Route path="/setores/novo" element={<PrivateRoute><NovoSetor /></PrivateRoute>} />
	<Route path="/setores/editar/:id" element={<PrivateRoute><EditarSetor /></PrivateRoute>} />
	<Route path="/movimentacoes/saida"element={<PrivateRoute><NovaSaida /></PrivateRoute>}/>
	<Route path="/lotes"element={<PrivateRoute><Lotes /></PrivateRoute>}/>
	<Route path="/lotes/produto/:id"element={<PrivateRoute><LotesProduto /></PrivateRoute>}/>
	<Route path="/auditoria"element={<PrivateRoute><Auditoria /></PrivateRoute>}/>
	<Route path="/dashboard" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
	<Route path="/prefeituras"element={<PrivateRoute><Prefeituras /></PrivateRoute>}/>
	<Route path="/prefeituras/nova"element={<PrivateRoute><PrefeituraForm /></PrivateRoute>}/>
	<Route path="/prefeituras/editar/:id"element={<PrivateRoute><PrefeituraForm /></PrivateRoute>}/>
	<Route path="/usuarios"element={<PrivateRoute><Usuarios /></PrivateRoute>}/>
	<Route path="/usuarios/novo"element={<PrivateRoute><UsuarioForm /></PrivateRoute>}/>
	<Route path="/usuarios/editar/:id"element={<PrivateRoute><UsuarioForm /></PrivateRoute>}/>
      </Routes>
    </BrowserRouter>
  );
}

export default AppRoutes;
