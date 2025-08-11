package capstone.probablymainserver.capstoneserver;

import java.sql.*;
import java.util.*;

public class capstone {
	public static int registerUser(String UserID, String PassWord) {
    	Connection conn = null;
    	PreparedStatement stmt = null;
		
        try {
        		conn = DatabaseUtil.getConnection();
        		
        		if (checkUserID(UserID) == true) { // 아이디 중복 검사
    				return 5; // 아이디 중복
    			}
        		
        		String sql = "INSERT INTO user (username, userpw, tool, allergy) VALUES (?, ?, ?, ?)";
        		stmt = conn.prepareStatement(sql);

        		stmt.setString(1, UserID);
        		stmt.setString(2, PassWord);
        		stmt.setLong(3, 0L);
        		stmt.setLong(4, 0);

        		int rows = stmt.executeUpdate();
        		if (rows > 0) { // 성공 여부 확인
        			return 1; // 회원 가입 성공
        		} else {
        			return 2; // 회원 가입 실패
        		}

        } catch (SQLException e) {
            e.printStackTrace();
            return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
        }
        
        finally {
        	DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
    }
	
	public static int Login(String UserID, String PassWord) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			if (!checkUserID(UserID)) { // 아이디 존재 확인
				return -3; // 존재하지 않는 아이디
			}
			
			String sql = "SELECT uid FROM user WHERE username = ? AND userpw = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, UserID);
			stmt.setString(2, PassWord);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				int uid = rs.getInt("uid");
				return uid; // 로그인 성공
			} else {
				return -4; // 비밀번호 틀림
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -2; // 코드 오류
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static User getUser(int uid) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int myUID;
		String userID;
		String pw;
		Long tools;
		Long banned;
		ArrayList<Ingredient> ing = new ArrayList<>();
		
		try {
			conn = DatabaseUtil.getConnection();
			
			String sql = "SELECT uid, username, userpw, tool, allergy FROM user WHERE uid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, uid);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				User user = new User();
				
				myUID = rs.getInt("uid");
				userID = rs.getString("username");
				pw = rs.getString("userpw");
				tools = rs.getLong("tool");
				banned = rs.getLong("allergy");
				ing = showRefrigerator(myUID);
				
				user.setUid(myUID);
				user.setUserId(userID);
				user.setPassword(pw);
				user.setTools(tools);
				user.setBanned(banned);
				user.setIngredients(ing);
				return user;
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static ArrayList<Ingredient> showRefrigerator(int uid) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ArrayList<Ingredient> ingredients = new ArrayList<>();
		
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "SELECT ingredientname FROM refrigerator WHERE uid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, uid);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				String name = rs.getString("ingredientname");
				int code = checkIngredientName(name);
				int type = checkIngredientType(code);
				
				Ingredient ing = new Ingredient();
				ing.setCode(code);
				ing.setName(name);
				ing.setType(type);
				ing.setCount(null);
				ing.setDescription(null);
				
				ingredients.add(ing);
			}
			
			return ingredients;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static boolean checkUserID(String UserID) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
        try {
        	conn = DatabaseUtil.getConnection();
        	String sql = "SELECT 1 FROM user WHERE username = ?";

        	stmt = conn.prepareStatement(sql);
        	stmt.setString(1, UserID);
        	
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
            	return true;
            } else {
            	return false;
            }
            
        } catch (SQLException e) {
        	e.printStackTrace();
        	return false;
        } finally {
        	DatabaseUtil.close(stmt);
        	DatabaseUtil.close(conn);
        }
	}
	
	public static int updateUserID(String newUserID, int uid) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			if (checkUserID(newUserID)) { // 아이디 존재 확인
				return 3; // 존재하는 아이디
			}
			
			String sql = "UPDATE user SET username = ? WHERE uid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, newUserID);
			stmt.setInt(2, uid);
						
			int rows = stmt.executeUpdate();
			if (rows > 0) { // 아이디 수정 성공 여부
				return 1; // 아이디 수정 성공
			} else {
				return 2; // 아이디 수정 실패
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static int updateUserPW(String newPassWord, int uid) {
		
		Connection conn = null;
		PreparedStatement checkStmt = null;
		PreparedStatement updateStmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			String checkSql = "SELECT userpw FROM user WHERE uid = ? AND userpw = ?";
			checkStmt = conn.prepareStatement(checkSql);
			checkStmt.setInt(1, uid);
			checkStmt.setString(2, newPassWord);
			ResultSet rs = checkStmt.executeQuery();
			
			if (rs.next()) {
				return 5; // 이미 사용중인 비밀번호
			}
			
			String updateSql = "UPDATE user SET userpw = ? WHERE uid = ?";
			updateStmt = conn.prepareStatement(updateSql);
			updateStmt.setString(1, newPassWord);
			updateStmt.setInt(2, uid);
						
			int rows = updateStmt.executeUpdate();
			if (rows > 0) { // 비밀번호 수정 성공 여부
				return 1; // 비밀번호 수정 성공
			} else {
				return 2; // 비밀번호 수정 실패
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
		} finally {
			DatabaseUtil.close(updateStmt);
			DatabaseUtil.close(checkStmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static int updateUserTools(Long tool, int uid) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			String sql = "UPDATE user SET tool = ? WHERE uid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, tool);
			stmt.setInt(2, uid);
						
			int rows = stmt.executeUpdate();
			if (rows > 0) { // 도구 수정 성공 여부
				return 1; // 도구 수정 성공
			} else {
				return 2; // 도구 수정 실패
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static int updateUserAllergy(Long allergy, int uid) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			String sql = "UPDATE user SET allergy = ? WHERE uid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, allergy);
			stmt.setInt(2, uid);
						
			int rows = stmt.executeUpdate();
			if (rows > 0) { // 알레르기 수정 성공 여부
				return 1; // 알레르기 수정 성공
			} else {
				return 2; // 알레르기 수정 실패
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static List<Ingredient> searchIngredients(String ingredient) {
		
		// 현재 db에 등록된 모든 재료를 보여주면 수가 1000개가 넘어가 이름을 통한 검색으로 재료를 보여주는 식으로 구현했습니다.
		Connection conn = null;
		PreparedStatement stmt = null;
		List<Ingredient> ingredients = new ArrayList<>();
		
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "SELECT ingredientname FROM ingredient WHERE ingredientname LIKE ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + ingredient + "%");
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				Ingredient ing = new Ingredient();
				
				String name = rs.getString("ingredientname");
				int code = checkIngredientName(name);
				int type = checkIngredientType(code);
				
				ing.setCode(code);
				ing.setName(name);
				ing.setType(type);
				ing.setCount(null);
				ing.setDescription(null);
				
				ingredients.add(ing);
			}
			
			return ingredients;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static int addIngredient(Ingredient ing, int uid) {
		String name = ing.getName();
		int code = ing.getCode();
		int type = checkIngredientType(code);
		Long allergy = checkAllergyCode(name);
		
		Connection conn = null;
		PreparedStatement stmt = null;

        try {
        		conn = DatabaseUtil.getConnection();
        		
        		if (checkIngredient(name, uid) == true) { // 재료 중복 검사
    				return 5; // 보유 중인 재료
    			}
        		
        		
        		String sql = "INSERT INTO refrigerator (ingredientname, ingredienttype, uid, allergy) VALUES (?, ?, ?, ?)";
        		stmt = conn.prepareStatement(sql);

        		stmt.setString(1, name);
        		stmt.setInt(2, type);
        		stmt.setInt(3, uid);
        		stmt.setLong(4, allergy);

        		int rows = stmt.executeUpdate();
        		if (rows > 0) { // 성공 여부 확인
        			return 1; // 재료 추가 성공
        		} else {
        			return 2; // 재료 추가 실패
        		}

        } catch (SQLException e) {
            e.printStackTrace();
            return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
        }
        
        finally {
        	DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static boolean checkIngredient(String ingredient, int uid) {
		String sql = "SELECT ingredientname FROM refrigerator WHERE ingredientname = ? AND uid = ?";

        try (
        	Connection conn = DatabaseUtil.getConnection();
        	PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
        	pstmt.setString(1, ingredient);
        	pstmt.setInt(2, uid);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
            	return true;
            } else {
            	return false;
            }
            
        } catch (SQLException e) {
        	e.printStackTrace();
        	return false;
        }
	}
	
	public static Long checkAllergyCode(String ingredient) {
		Connection conn = null;
		PreparedStatement stmt = null;

        try {

        	conn = DatabaseUtil.getConnection();
        	
        	String sql = "SELECT allergycode FROM ingredient WHERE ingredientname = ?";
        	stmt = conn.prepareStatement(sql);
        	stmt.setString(1, ingredient);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
            	Long code = rs.getLong("allergycode");
            	return code;
            } else {
            	return 0L;
            }
            
        } catch (SQLException e) {
        	e.printStackTrace();
        	return 0L;
        } finally {
        	DatabaseUtil.close(stmt);
        	DatabaseUtil.close(conn);
        }
	}
	
	public static int removeIngredient(Ingredient ing, int uid) {
		String name = ing.getName();
		
		Connection conn = null;
		PreparedStatement stmt = null;

        try {
        		conn = DatabaseUtil.getConnection();
        		        		
        		String sql = "DELETE FROM refrigerator WHERE ingredientname = ? AND uid = ?";
        		stmt = conn.prepareStatement(sql);

        		stmt.setString(1, name);
        		stmt.setInt(2, uid);
        		
        		int rows = stmt.executeUpdate();
        		if (rows > 0) { // 성공 여부 확인
        			return 1; // 재료 삭제 성공
        		} else {
        			return 2; // 재료 삭제 실패
        		}

        } catch (SQLException e) {
            e.printStackTrace();
            return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
        }
        
        finally {
        	DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static int clearIngredient(int uid) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "DELETE FROM refrigerator WHERE uid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, uid);
			int rows = stmt.executeUpdate();
			if(rows > 0) {
				return 1;
			} else {
				return 2;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			return 2;
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static List<Recipe> RecipeListSearchFromDB(List<String> ingredients, int uid){
		ArrayList<Recipe> l = new ArrayList<>();
		List<Integer> resultRID = null;
		
		for (String ingr : ingredients) {
			List<Integer> current = searchCodeInDB(ingr, uid);
			
			if (current == null) {
				continue;
			}
			
			if (resultRID == null) {
				resultRID = new ArrayList<>(current);
			} else {
				resultRID.retainAll(current);
			}
		}
		
		if (resultRID == null || resultRID.isEmpty()) {
			return new ArrayList<>();
		}
		
		for (int rid : resultRID) {
			Recipe r = new Recipe();
			r = RecipeByCode(rid, uid);
			if (r != null) {
				l.add(r);
			}
		}
		
		return l;
	}
	
	public static List<Integer> searchCodeInDB(String ingr, int uid){
		Connection conn = null;
		PreparedStatement stmt = null;
		ArrayList<Integer> list = new ArrayList<>();
		int rid;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			String sql = "SELECT rid FROM community WHERE foodingredient LIKE ? AND uid = ? AND checkcode = 0";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + ingr + "%");
			stmt.setInt(2, uid);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				rid = rs.getInt("rid");
				list.add(rid);
			}
			
			return list;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static Recipe RecipeByCode(int code, int uid) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		//String author;
		String name;
		String foodingredient;
		Ingredient main = new Ingredient();
		ArrayList<Ingredient> sub = new ArrayList<>();
		int maincode;
		String mainname;
		int maintype;
		int subcode;
		int subtype;
		Long tools;
		int foodtype;
		String foodrecipe;
		ArrayList<String> recipes = new ArrayList<>();
		String time;
		Recipe recipe = new Recipe();
		int checkcode;
		Long allergy;
		
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "SELECT foodname, foodingredient, mainingredientcode, tool, foodrecipe, foodtype, time, checkcode, allergy FROM community WHERE rid = ? AND uid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, code);
			stmt.setInt(2, uid);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				name = rs.getString("foodname");
				foodingredient = rs.getString("foodingredient");
				maincode = rs.getInt("mainingredientcode");
				tools = rs.getLong("tool");
				foodrecipe = rs.getString("foodrecipe");
				foodtype = rs.getInt("foodtype");
				time = rs.getString("time");
				checkcode = rs.getInt("checkcode");
				allergy = rs.getLong("allergy");
				//author = checkUserName(uid);
				
				recipe.setCode(code);
				//recipe.setAuthor(author);
				recipe.setName(name);
				recipe.setTools(tools);
				recipes.add(foodrecipe);
				recipe.setRecipe(recipes);
				recipe.setTime(time);
				mainname = checkIngredientCode(maincode);
				maintype = checkIngredientType(maincode);
				main.setCode(maincode);
				main.setName(mainname);
				main.setType(maintype);
				main.setCount(null);
				main.setDescription(null);
				recipe.addMainIngredients(main);
				for (String subname : foodingredient.split(",")) {
	                Ingredient ing = new Ingredient();
	                ing.setName(subname.trim());
	                subcode = checkIngredientName(subname);
	                ing.setCode(subcode);
	                subtype = checkIngredientType(subcode);
	                ing.setType(subtype);
	                ing.setCount(null);
	                ing.setDescription(null);
	                sub.add(ing);
	            }
				recipe.setSubIngredients(sub);
				
				return recipe;
			} else {
				return null;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static String checkUserName(int userID) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "SELECT username FROM user WHERE uid = ?";
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userID);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString("username");
			} else {
				return null;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static List<Recipe> RecipeListSearchFromAILike(List<String> ingredients, int uid){
		ArrayList<Recipe> l = new ArrayList<>();
		List<Integer> resultRID = null;
		
		for (String ingr : ingredients) {
			List<Integer> current = searchCodeInAILike(ingr, uid);
			
			if (current == null) {
				continue;
			}
			
			if (resultRID == null) {
				resultRID = new ArrayList<>(current);
			} else {
				resultRID.retainAll(current);
			}
		}
		
		if (resultRID == null || resultRID.isEmpty()) {
			return new ArrayList<>();
		}
		
		for (int rid : resultRID) {
			Recipe r = new Recipe();
			r = RecipeByCode(rid, uid);
			if (r != null) {
				l.add(r);
			}
		}
		
		return l;
	}
	
	public static List<Integer> searchCodeInAILike(String ingr, int uid){
		Connection conn = null;
		PreparedStatement stmt = null;
		ArrayList<Integer> list = new ArrayList<>();
		int rid;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			String sql = "SELECT rid FROM community WHERE foodingredient LIKE ? AND uid = ? AND checkcode = 1";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + ingr + "%");
			stmt.setInt(2, uid);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				rid = rs.getInt("rid");
				list.add(rid);
			}
			
			return list;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static int RecipeAddInDB(Recipe recipe, int uid) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			String FoodName = recipe.getName();
			
			ArrayList<Ingredient> main = recipe.getMainIngredients();
			ArrayList<Ingredient> sub = recipe.getSubIngredients();
			
			StringBuilder sb = new StringBuilder();
			
			for (Ingredient ingr : main) {
				sb.append(ingr.getName()).append(",");
			}
			
			for (Ingredient ingr : sub) {
				sb.append(ingr.getName()).append(",");
			}
			
			if (sb.length() > 0) {
				sb.setLength(sb.length() -1);
			}
			
			String FoodIngredients = sb.toString();
			String mainname = !main.isEmpty() ? main.get(0).getName() : null;
			Long tool = recipe.getTools();
			ArrayList<String> FoodRecipe = recipe.getRecipe();
			String foodrecipe = String.join("\n", FoodRecipe);
			String time = recipe.getTime();
			int maincode = (mainname != null) ? checkIngredientName(mainname) : 0;
			
			String sql = "INSERT INTO community (foodname, foodingredient, mainingredientcode, tool, foodrecipe, foodtype, time, uid, checkcode, allergy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, FoodName);
			stmt.setString(2, FoodIngredients);
			stmt.setInt(3, maincode);
			stmt.setLong(4, tool);
			stmt.setString(5, foodrecipe);
			stmt.setInt(6, 0);
			stmt.setString(7, time);
			stmt.setInt(8, uid);
			stmt.setInt(9, 0);
			stmt.setLong(10, 0L);
			
			int rows = stmt.executeUpdate();
			if(rows > 0) { // 레시피 등록 성공 여부
				// 이 부분에 레시피 등록 메소드를 작성해주세요.
				return 1; // 성공
			} else {
				return 2; // 실패
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static int checkRecipeType(int rid) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		int code = 0;
		
        try {
        	
        	conn = DatabaseUtil.getConnection();
        	String sql = "SELECT checkcode FROM community WHERE rid = ?";

        	stmt = conn.prepareStatement(sql);
        	stmt.setInt(1, rid);
        	
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
            	code = rs.getInt("checkcode");
            }
            
            if(code == 1) {
            	return code;
            } else {
            	return code;
            }
            
        } catch (SQLException e) {
        	e.printStackTrace();
        	return 2;
        } finally {
        	DatabaseUtil.close(stmt);
        	DatabaseUtil.close(conn);
        }
	}
	
	public static int RecipeDeleteInDB(int recipeCode, int uid) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			if(checkRecipeType(recipeCode) == 1) {
				return 2; // 찜한 레시피
			}
			
			conn = DatabaseUtil.getConnection();
			
			String sql = "DELETE FROM community WHERE rid = ? AND uid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, recipeCode);
			stmt.setInt(2, uid);
			
			int rows = stmt.executeUpdate();
			if(rows > 0) { // 레시피 삭제 성공 여부
				// 이 부분에 레시피 삭제 메소드를 작성해주세요.
				return 1; // 성공
			} else {
				return 2; // 실패
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static int RecipeUpdateInDB(Recipe recipeData, int recipeCode) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String foodName = null;
		
		int maincode = 0;
		Long tool;
		ArrayList<String> FoodRecipe = null;
		String foodrecipe;
		String time;
		
		try {
			if(checkRecipeType(recipeCode) == 1) {
				return 2; // 찜한 레시피
			}
			
			conn = DatabaseUtil.getConnection();
			
			foodName = recipeData.getName();
			ArrayList<Ingredient> main = recipeData.getMainIngredients();
			ArrayList<Ingredient> sub = recipeData.getSubIngredients();
			StringBuilder sb = new StringBuilder();
			
			for (Ingredient ingr : main) {
				sb.append(ingr.getName()).append(",");
			}
			
			for (Ingredient ingr : sub) {
				sb.append(ingr.getName()).append(",");
			}
			
			if (sb.length() > 0) {
				sb.setLength(sb.length() -1);
			}
			
			String FoodIngredients = sb.toString();
			String mainname = !main.isEmpty() ? main.get(0).getName() : null;
			tool = recipeData.getTools();
			FoodRecipe = recipeData.getRecipe();
			foodrecipe = FoodRecipe.toString();
			time = recipeData.getTime();
			maincode = (mainname != null) ? checkIngredientName(mainname) : 0;
			
			String sql = "UPDATE community SET foodname = ?, foodingredient = ?, mainingredientcode = ?, tool = ?, foodrecipe = ?, time = ? WHERE rid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, foodName);
			stmt.setString(2, FoodIngredients);
			stmt.setInt(3, maincode);
			stmt.setLong(4, tool);
			stmt.setString(5, foodrecipe);
			stmt.setString(6, time);
			stmt.setInt(7, recipeCode);
			
			int rows = stmt.executeUpdate();
			if(rows > 0) { // 레시피 수정 성공 여부
				return 1; // 성공
			} else {
				return 2; // 실패
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static List<Recipe> showRecipeInDB(int uid){
		ArrayList<Recipe> l = new ArrayList<>();
		List<Integer> resultRID = LoadRecipeInDB(uid);
		
		for (int rid : resultRID) {
			Recipe r = new Recipe();
			r = RecipeByCode(rid, uid);
			if (r != null) {
				l.add(r);
			}
		}
		
		return l;
	}
	
	public static List<Integer> LoadRecipeInDB(int uid){
		Connection conn = null;
		PreparedStatement stmt = null;
		
		ArrayList<Integer> ridList = new ArrayList<>();
		try {
			conn = DatabaseUtil.getConnection();
			
			String sql = "SELECT rid FROM community WHERE uid = ? AND checkcode = 0";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, uid);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				int rid = rs.getInt("rid");
				ridList.add(rid);
			}
			
			return ridList;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static int RecipeAddInAILike(Recipe recipe, int uid) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			
			String FoodName = recipe.getName();
			
			ArrayList<Ingredient> main = recipe.getMainIngredients();
			ArrayList<Ingredient> sub = recipe.getSubIngredients();
			
			StringBuilder sb = new StringBuilder();
			
			for (Ingredient ingr : main) {
				sb.append(ingr.getName()).append(",");
			}
			
			for (Ingredient ingr : sub) {
				sb.append(ingr.getName()).append(",");
			}
			
			if (sb.length() > 0) {
				sb.setLength(sb.length() -1);
			}
			
			String FoodIngredients = sb.toString();
			String mainname = !main.isEmpty() ? main.get(0).getName() : null;
			Long tool = recipe.getTools();
			ArrayList<String> FoodRecipe = recipe.getRecipe();
			String foodrecipe = String.join("\n", FoodRecipe);
			String time = recipe.getTime();
			int maincode = (mainname != null) ? checkIngredientName(mainname) : 0;
			
			String sql = "INSERT INTO community (foodname, foodingredient, mainingredientcode, tool, foodrecipe, foodtype, time, uid, checkcode, allergy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, FoodName);
			stmt.setString(2, FoodIngredients);
			stmt.setInt(3, maincode);
			stmt.setLong(4, tool);
			stmt.setString(5, foodrecipe);
			stmt.setInt(6, 0);
			stmt.setString(7, time);
			stmt.setInt(8, uid);
			stmt.setInt(9, 1);
			stmt.setLong(10, 0L);
			
			int rows = stmt.executeUpdate();
			if(rows > 0) { // 레시피 등록 성공 여부
				// 이 부분에 레시피 등록 메소드를 작성해주세요.
				return 1; // 성공
			} else {
				return 2; // 실패
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
		
	public static int RecipeDeleteInAILike(int recipeCode, int uid) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		
		try {
			if(checkRecipeType(recipeCode) == 0) {
				return 2; // 개인 등록 레시피
			}
			
			conn = DatabaseUtil.getConnection();
			
			String sql = "DELETE FROM community WHERE rid = ? AND uid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, recipeCode);
			stmt.setInt(2, uid);
			
			int rows = stmt.executeUpdate();
			if(rows > 0) { // 레시피 삭제 성공 여부
				// 이 부분에 레시피 삭제 메소드를 작성해주세요.
				return 1; // 성공
			} else {
				return 2; // 실패
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return 2; // 에러 발생 (java 코드 문법 틀림 / db 키 조건 부적합 등)
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static List<Recipe> showRecipeInAILike(int uid){
		ArrayList<Recipe> l = new ArrayList<>();
		List<Integer> resultRID = LoadRecipeInDB(uid);
		
		for (int rid : resultRID) {
			Recipe r = new Recipe();
			r = RecipeByCode(rid, uid);
			if (r != null) {
				l.add(r);
			}
		}
		
		return l;
	}
	
	public static List<Integer> LoadRecipeInAILike(int uid){
		Connection conn = null;
		PreparedStatement stmt = null;
		
		ArrayList<Integer> ridList = new ArrayList<>();
		try {
			conn = DatabaseUtil.getConnection();
			
			String sql = "SELECT rid FROM community WHERE uid = ? AND checkcode = 1";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, uid);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				int rid = rs.getInt("rid");
				ridList.add(rid);
			}
			
			return ridList;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static int checkIngredientName(String ingr) {
		int code = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "SELECT ingredientno FROM ingredient WHERE ingredientname = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, ingr);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				code = rs.getInt("ingredientno");
				return code;
			} else {
				return 0;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static String checkIngredientCode(int ingr) {
		String name = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "SELECT ingredientname FROM ingredient WHERE ingredientno = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ingr);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				name = rs.getString("ingredientname");
				return name;
			} else {
				return null;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
	
	public static int checkIngredientType(int ingr) {
		int type = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			String sql = "SELECT ingredienttype FROM ingredient WHERE ingredientno = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ingr);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				type = rs.getInt("ingredienttype");
				return type;
			} else {
				return 0;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			DatabaseUtil.close(stmt);
			DatabaseUtil.close(conn);
		}
	}
}
