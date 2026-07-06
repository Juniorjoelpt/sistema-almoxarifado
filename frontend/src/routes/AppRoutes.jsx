import { BrowserRouter, Routes, Route } from "react-router-dom";
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
        <Route path="/" element={<Dashboard />} />
        <Route path="/produtos" element={<Produtos />} />
        <Route path="/login" element={<Login />} /> {/* Rota adicionada */}
	<Route  path="/produtos/novo"element= {<NovoProduto />}/>
	<Route	 path="/categorias"element={<Categorias />}/>
	<Route  path="/produtos/editar/:id"element={<EditarProduto />}/>
	<Route path="/movimentacoes"element={<Movimentacoes />}/>
	<Route path="/movimentacoes/entrada"element={<NovaEntrada />}/>
	<Route path="/fornecedores"element={<Fornecedores />}/>
	<Route path="/fornecedores/novo"element={<NovoFornecedor />}/>
	<Route path="/fornecedores/editar/:id"element={<EditarFornecedor />}/>
	<Route path="/setores" element={<Setores />} />
	<Route path="/setores/novo" element={<NovoSetor />} />
	<Route path="/setores/editar/:id" element={<EditarSetor />} />
	<Route path="/movimentacoes/saida"element={<NovaSaida />}/>
	<Route path="/lotes"element={<Lotes />}/>
	<Route path="/lotes/produto/:id"element={<LotesProduto />}/>
	<Route path="/auditoria"element={<Auditoria />}/>
	<Route path="/" element={<Dashboard />} />
	<Route path="/dashboard" element={<Dashboard />} />
	<Route path="/prefeituras"element={<Prefeituras />}/>
	<Route path="/prefeituras/nova"element={<PrefeituraForm />}/>
	<Route path="/prefeituras/editar/:id"element={<PrefeituraForm />}/>
	<Route path="/usuarios"element={<Usuarios />}/>
	<Route path="/usuarios/novo"element={<UsuarioForm />}/>
	<Route path="/usuarios/editar/:id"element={<UsuarioForm />}/>
      </Routes>
    </BrowserRouter>
  );
}

export default AppRoutes;