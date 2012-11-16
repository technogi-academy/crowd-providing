package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Category extends Model{

	public String name;
	@ManyToMany
	public List<Supplier> suppliers;
}
